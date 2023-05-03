-- CS4400: Introduction to Database Systems: Wednesday, March 8, 2023
-- Flight Management Course Project Mechanics (v1.0) STARTING SHELL
-- Views, Functions & Stored Procedures

/* This is a standard preamble for most of our scripts.  The intent is to establish
a consistent environment for the database behavior. */
set global transaction isolation level serializable;
set global SQL_MODE = 'ANSI,TRADITIONAL';
set names utf8mb4;
set SQL_SAFE_UPDATES = 0;
set @thisDatabase = 'flight_management';

use flight_management;
-- -----------------------------------------------------------------------------
-- stored procedures and views
-- -----------------------------------------------------------------------------
/* Standard Procedure: If one or more of the necessary conditions for a procedure to
be executed is false, then simply have the procedure halt execution without changing
the database state. Do NOT display any error messages, etc. */

-- [1] add_airplane()
-- -----------------------------------------------------------------------------
/* This stored procedure creates a new airplane.  A new airplane must be sponsored
by an existing airline, and must have a unique tail number for that airline.
username.  An airplane must also have a non-zero seat capacity and speed. An airplane
might also have other factors depending on it's type, like skids or some number
of engines.  Finally, an airplane must have a database-wide unique location if
it will be used to carry passengers. */
-- -----------------------------------------------------------------------------
drop procedure if exists add_airplane;
delimiter //
create procedure add_airplane (in ip_airlineID varchar(50), in ip_tail_num varchar(50),
	in ip_seat_capacity integer, in ip_speed integer, in ip_locationID varchar(50),
    in ip_plane_type varchar(100), in ip_skids boolean, in ip_propellers integer,
    in ip_jet_engines integer)
sp_main: begin

	if (ip_airlineID in (select airlineID from airline)) and 
    (ip_tail_num not in (select tail_num from airplane)) and
	(ip_seat_capacity is not null) and  (ip_speed is not null)
	then 
    insert into airplane (airlineID, tail_num, seat_capacity, speed, locationID, plane_type, skids, propellers, jet_engines)
	values (ip_airlineID, ip_tail_num, ip_seat_capacity, ip_speed, ip_locationID, ip_plane_type, ip_skids, ip_propellers, ip_jet_engines);
	end if;


end //
delimiter ;

-- [2] add_airport()
-- -----------------------------------------------------------------------------
/* This stored procedure creates a new airport.  A new airport must have a unique
identifier along with a database-wide unique location if it will be used to support
airplane takeoffs and landings.  An airport may have a longer, more descriptive
name.  An airport must also have a city and state designation. */
-- -----------------------------------------------------------------------------
drop procedure if exists add_airport;
delimiter //
create procedure add_airport (in ip_airportID char(3), in ip_airport_name varchar(200),
    in ip_city varchar(100), in ip_state char(2), in ip_locationID varchar(50))
sp_main: begin

	if (ip_airportID not in (select airportID from airport)) and
	(ip_city is not null) and  (ip_state is not null)
	then 
    insert into airport (airportID, airport_name, city, state, locationID)
	values (ip_airportID, ip_airport_name, ip_city, ip_state, ip_locationID);
	end if;


end //
delimiter ;

-- [3] add_person()
-- -----------------------------------------------------------------------------
/* This stored procedure creates a new person.  A new person must reference a unique
identifier along with a database-wide unique location used to determine where the
person is currently located: either at an airport, or on an airplane, at any given
time.  A person may have a first and last name as well.

Also, a person can hold a pilot role, a passenger role, or both roles.  As a pilot,
a person must have a tax identifier to receive pay, and an experience level.  Also,
a pilot might be assigned to a specific airplane as part of the flight crew.  As a
passenger, a person will have some amount of frequent flyer miles. */
-- -----------------------------------------------------------------------------
drop procedure if exists add_person;
delimiter //
create procedure add_person (in ip_personID varchar(50), in ip_first_name varchar(100),
    in ip_last_name varchar(100), in ip_locationID varchar(50), in ip_taxID varchar(50),
    in ip_experience integer, in ip_flying_airline varchar(50), in ip_flying_tail varchar(50),
    in ip_miles integer)
sp_main: begin

	if (ip_personID not in (select personID from person)) and
    (ip_first_name is not null) and (ip_last_name is not null)
    then
    insert into person (personID, first_name, last_name, locationID)
	values (ip_personID, ip_first_name, ip_last_name, ip_locationID);
    
    insert into passenger (personID, miles)
    values (ip_personID, ip_miles);
	end if;
    
    if (ip_personID not in (select personID from person)) and
    ((ip_taxID is not null) and (ip_taxID not in (select taxID from pilot)))
    then
    insert into pilot (personID, taxID, experience, flying_airline, flying_tail)
    values (ip_personID, ip_taxID, ip_experience, ip_flying_airline, ip_flying_tail);
    end if;

end //
delimiter ;

-- [4] grant_pilot_license()
-- -----------------------------------------------------------------------------
/* This stored procedure creates a new pilot license.  The license must reference
a valid pilot, and must be a new/unique type of license for that pilot. */
-- -----------------------------------------------------------------------------
drop procedure if exists grant_pilot_license;
delimiter //
create procedure grant_pilot_license (in ip_personID varchar(50), in ip_license varchar(100))
sp_main: begin

	if (ip_personID in (select personID from pilot)) and
    ((ip_license not in (select license from pilot_licenses where personID = ip_personID)))
    then
    insert into pilot_licenses (personID, license)
    values (ip_personID, ip_license);
    end if;

end //
delimiter ;

-- [5] offer_flight()
-- -----------------------------------------------------------------------------
/* This stored procedure creates a new flight.  The flight can be defined before
an airplane has been assigned for support, but it must have a valid route.  Once
an airplane has been assigned, we must also track where the airplane is along
the route, whether it is in flight or on the ground, and when the next action -
takeoff or landing - will occur. */
-- -----------------------------------------------------------------------------
drop procedure if exists offer_flight;
delimiter //
create procedure offer_flight (in ip_flightID varchar(50), in ip_routeID varchar(50),
    in ip_support_airline varchar(50), in ip_support_tail varchar(50), in ip_progress integer,
    in ip_airplane_status varchar(100), in ip_next_time time)
sp_main: begin

	if (ip_flightID not in (select flightID from flight)) and
    (ip_routeID in (select routeID from route)) and
    (ip_airplane_status is not null) and (ip_next_time is not null)
    then
    insert into flight (flightID, routeID, support_airline, support_tail, progress, airplane_status, next_time)
    values (ip_flightID, ip_routeID, ip_support_airline, ip_support_tail, ip_progress, ip_airplane_status, ip_next_time);
    end if;

end //
delimiter ;

-- [6] purchase_ticket_and_seat()
-- -----------------------------------------------------------------------------
/* This stored procedure creates a new ticket.  The cost of the flight is optional
since it might have been a gift, purchased with frequent flyer miles, etc.  Each
flight must be tied to a valid person for a valid flight.  Also, we will make the
(hopefully simplifying) assumption that the departure airport for the ticket will
be the airport at which the traveler is currently located.  The ticket must also
explicitly list the destination airport, which can be an airport before the final
airport on the route.  Finally, the seat must be unoccupied. */
-- -----------------------------------------------------------------------------
drop procedure if exists purchase_ticket_and_seat;
delimiter //
create procedure purchase_ticket_and_seat (in ip_ticketID varchar(50), in ip_cost integer,
	in ip_carrier varchar(50), in ip_customer varchar(50), in ip_deplane_at char(3),
    in ip_seat_number varchar(50))
sp_main: begin

	if (ip_customer in (select personID from person)) 
    and (ip_carrier in (select flightID from flight)) and (ip_deplane_at in (select airportID from airport)) 
    and (ip_seat_number not in (select seat_number from ticket_seats where ticketID = ip_ticketID))
    then
    insert into ticket (ticketID, cost, carrier, customer, deplane_at)
    values (ip_ticketID, ip_cost, ip_carrier, ip_customer, ip_deplane_at);

    insert into ticket_seats (ticketID, seat_number)
    values (ip_ticketID, ip_seat_number);
    end if;

end //
delimiter ;

-- [7] add_update_leg()
-- -----------------------------------------------------------------------------
/* This stored procedure creates a new leg as specified.  However, if a leg from
the departure airport to the arrival airport already exists, then don't create a
new leg - instead, update the existence of the current leg while keeping the existing
identifier.  Also, all legs must be symmetric.  If a leg in the opposite direction
exists, then update the distance to ensure that it is equivalent.   */
-- -----------------------------------------------------------------------------
drop procedure if exists add_update_leg;
delimiter //
create procedure add_update_leg (in ip_legID varchar(50), in ip_distance integer,
    in ip_departure char(3), in ip_arrival char(3))
sp_main: begin

	-- Check if there are same departure & arrival
	if (ip_arrival in (select arrival from leg)) and
    (ip_departure in (select departure from leg where arrival = ip_arrival))
    then
    update leg
    set distance = ip_distance
    where arrival = ip_arrival and departure = ip_departure;
    
    if (ip_departure in (select arrival from leg)) and
    (ip_arrival in (select departure from leg where arrival = ip_departure))
    then
    update leg
    set distance = ip_distance
    where departure = ip_arrival and arrival = ip_departure;
    end if;
    
    -- For the new leg
    else
    insert into leg (legID, distance, departure, arrival)
    values (ip_legID, ip_distance, ip_departure, ip_arrival);
	end if;

end //
delimiter ;

-- [8] start_route()
-- -----------------------------------------------------------------------------
/* This stored procedure creates the first leg of a new route.  Routes in our
system must be created in the sequential order of the legs.  The first leg of
the route can be any valid leg. */
-- -----------------------------------------------------------------------------
drop procedure if exists start_route;
delimiter //
create procedure start_route (in ip_routeID varchar(50), in ip_legID varchar(50))
sp_main: begin

		-- Add to route table
	insert into route (routeID)
    values (ip_routeID);
    
    -- Add to route_path
    insert into route_path (routeID, legID, sequence)
    values (ip_routeID, ip_legID, 1);

end //
delimiter ;

-- [9] extend_route()
-- -----------------------------------------------------------------------------
/* This stored procedure adds another leg to the end of an existing route.  Routes
in our system must be created in the sequential order of the legs, and the route
must be contiguous: the departure airport of this leg must be the same as the
arrival airport of the previous leg. */
-- -----------------------------------------------------------------------------
drop procedure if exists extend_route;
delimiter //
create procedure extend_route (in ip_routeID varchar(50), in ip_legID varchar(50))
sp_main: begin

	set @count := (select count(*) from route_path where routeID = ip_routeID);
	insert into route_path (routeID, legID, sequence)
    values (ip_routeID, ip_legID, 1 + @count);

end //
delimiter ;

-- [10] flight_landing()
-- -----------------------------------------------------------------------------
/* This stored procedure updates the state for a flight landing at the next airport
along it's route.  The time for the flight should be moved one hour into the future
to allow for the flight to be checked, refueled, restocked, etc. for the next leg
of travel.  Also, the pilots of the flight should receive increased experience, and
the passengers should have their frequent flyer miles updated. */
-- -----------------------------------------------------------------------------
drop procedure if exists flight_landing;
delimiter //
create procedure flight_landing (in ip_flightID varchar(50))
sp_main: begin

	if ISNULL(ip_flightID) then leave sp_main; end if;

	IF 	ip_flightID not in (select flightID from flight)
		OR  (select airplane_status from flight where flightID=ip_flightID) = 'on_ground'
		then leave sp_main;
	end if;
    
    SELECT support_tail, support_airline, routeID, progress INTO @fTail, @fAirline, @rID, @seq from flight where flightID=ip_flightID;
    if ISNULL(@fTail) or ISNULL(@fAirline) then leave sp_main; end if;
	SET @locID = (select locationID from airplane where tail_num = @fTail and airlineID = @fAirline);
    
	UPDATE flight
	SET airplane_status = 'on_ground', next_time = ADDTIME(next_time, '01:00:00')
    WHERE flightID=ip_flightID; 
    
    UPDATE pilot
    SET experience = experience + 1
    WHERE flying_tail = @fTail and flying_airline = @fAirline;
    
    SET @distance = (select distance from route_path inner join leg on route_path.legID=leg.legID where routeID = @rID and sequence = @seq);
    
    UPDATE passenger
    SET miles = miles + @distance
    where personID in (select personID from person where locationID = @locID);

end //
delimiter ;

-- [11] flight_takeoff()
-- -----------------------------------------------------------------------------
/* This stored procedure updates the state for a flight taking off from its current
airport towards the next airport along it's route.  The time for the next leg of
the flight must be calculated based on the distance and the speed of the airplane.
And we must also ensure that propeller driven planes have at least one pilot
assigned, while jets must have a minimum of two pilots. If the flight cannot take
off because of a pilot shortage, then the flight must be delayed for 30 minutes. */
-- -----------------------------------------------------------------------------
drop procedure if exists flight_takeoff;
delimiter //
create procedure flight_takeoff (in ip_flightID varchar(50))
sp_main: begin

	if ISNULL(ip_flightID) then leave sp_main; end if;

	if ip_flightID not in (select flightID from flight) then leave sp_main; end if;
    SELECT support_tail, support_airline, routeID, progress INTO @fTail, @fAirline, @rID, @seq from flight where flightID=ip_flightID;
    if ISNULL(@fTail) or ISNULL(@fAirline) then leave sp_main; end if;
    if (select COUNT(*) from route_path where routeID = @rID) = 0 then leave sp_main; end if;
	select plane_type, speed into @plane_type, @speed from airplane where tail_num = @fTail and airlineID = @fAirline;
	IF 	@plane_type = 'prop' and (select count(*) from pilot where flying_tail = @fTail and flying_airline = @fAirline) < 1 
		OR @plane_type = 'jet'  and (select count(*) from pilot where flying_tail = @fTail and flying_airline = @fAirline) < 2
	THEN
		UPDATE flight SET next_time = ADDTIME(next_time, '00:30:00') WHERE flightID = ip_flightID; leave sp_main;
	END IF;
	
    SET @seq = @seq + 1;
	SET @lID = (select legID from route_path where routeID = @rID and sequence = @seq);
    SET @distance = (select distance from leg where legID = @lID);
    SET @added_hrs = @distance/@speed;
    
    UPDATE flight SET airplane_status = 'in_flight', progress = @seq, next_time = DATE_ADD(next_time, INTERVAL @added_hrs HOUR) WHERE flightID = ip_flightID; 
end //
delimiter ;

-- [12] passengers_board()
-- -----------------------------------------------------------------------------
/* This stored procedure updates the state for passengers getting on a flight at
its current airport.  The passengers must be at the airport and hold a valid ticket
for the flight. */
-- -----------------------------------------------------------------------------
drop procedure if exists passengers_board;
delimiter //
create procedure passengers_board (in ip_flightID varchar(50))
sp_main: begin

	if ISNULL(ip_flightID) then leave sp_main; end if;
    
    if ip_flightID not in (select flightID from flight) then leave sp_main; end if;

	SELECT airplane_status, routeID, progress, support_tail, support_airline INTO @ap_status, @rID, @seq, @ft, @fAirline FROM flight WHERE flightID = ip_flightID;

	if ISNULL(@ap_status) or ISNULL(@seq) or ISNULL(@ft) or ISNULL(@fAirline) then leave sp_main; end if;
    
    if @ap_status = 'in_flight' then leave sp_main; end if;
    
	SET @lID = (select legID from route_path where routeID = @rID and sequence = @seq + 1);
    SET @cur_dep = (select departure from leg where legID = @lID);
    SET @cur_loc = (select locationID from airport where airportID = @cur_dep);

    SET @airp_loc = (select locationID from airplane where tail_num = @fT and airlineID = @fAirline);
    
    UPDATE person SET locationID = @airp_loc WHERE locationID = @cur_loc and personID in (select customer from ticket where carrier = ip_flightID);

end //
delimiter ;

-- [13] passengers_disembark()
-- -----------------------------------------------------------------------------
/* This stored procedure updates the state for passengers getting off of a flight
at its current airport.  The passengers must be on that flight, and the flight must
be located at the destination airport as referenced by the ticket. */
-- -----------------------------------------------------------------------------
drop procedure if exists passengers_disembark;
delimiter //
create procedure passengers_disembark (in ip_flightID varchar(50))
sp_main: begin

	if ip_flightID not in (select flightID from flight) then leave sp_main; end if;
    
    select airplane_status, routeID, progress, support_tail, support_airline INTO @ap_status, @rID, @prog, @fTail, @fAirline from flight
	where flightID = ip_flightID;
    
    if @ap_status != 'on_ground' then leave sp_main; end if;
    
    set @lID = (select legID from route_path where routeID = @rID and sequence = @prog);
    set @airpID = (select arrival from leg where legID = @lID);
    if ISNULL(@airpID) then leave sp_main; end if;
    
    SET @locID = (select locationID from airport where airportID = @airpID);
    
    UPDATE person SET locationID = @locID where personID in (select customer from ticket where carrier = ip_flightID and deplane_at = @airpID);

end //
delimiter ;

-- [14] assign_pilot()
-- -----------------------------------------------------------------------------
/* This stored procedure assigns a pilot as part of the flight crew for a given
airplane.  The pilot being assigned must have a license for that type of airplane,
and must be at the same location as the flight.  Also, a pilot can only support
one flight (i.e. one airplane) at a time.  The pilot must be assigned to the flight
and have their location updated for the appropriate airplane. */
-- -----------------------------------------------------------------------------
drop procedure if exists assign_pilot;
delimiter //
create procedure assign_pilot (in ip_flightID varchar(50), ip_personID varchar(50))
sp_main: begin

	if ip_flightID not in (select flightID from flight) then leave sp_main; end if;
    if ip_personID not in (select personID from pilot) then leave sp_main; end if;
    
    SELECT flying_tail, flying_airline INTO @fTP, @fAirlineP from pilot where personID = ip_personID;
    if not ISNULL(@fTP) or not ISNULL(@fAirlineP) then leave sp_main; end if;
    
    SELECT flightID, routeID, progress, airplane_status, support_tail, support_airline into @fID, @rID, @seq, @airplane_status, @fT, @fAirline
    from flight where flightID = ip_flightID;
    if ISNULL(@fT) or ISNULL(@fAirline) or ISNULL(@airplane_status) or ISNULL(@seq) then leave sp_main; end if;
    if @airplane_status != 'on_ground' then leave sp_main; end if;
    if (select count(*) from route_path where routeID = @rID and sequence = @seq + 1) = 0 then leave sp_main; end if;
    
    select plane_type, locationID into @plane_type, @locID from airplane where tail_num = @fT and airlineID = @fAirline;
    if ISNULL(@locID) then leave sp_main; end if;
    if not ISNULL(@plane_type) and not (ip_personID, @plane_type) in (select * from pilot_licenses) then leave sp_main; end if;
    
    SET @lID = (select legID from route_path where routeID = @rID and sequence = @seq + 1);
    SET @airpID = (select departure from leg where legID = @lID);
    SET @oldLoc = (select locationID from airport where airportID = @airpID);
    
    if ISNULL(@oldLoc) then leave sp_main; end if; 
    SET @pilotLoc = (select locationID from person where personId = ip_personID);
    if @pilotLoc != @oldLoc then leave sp_main; end if;

    UPDATE pilot SET flying_tail = @fT, flying_airline = @fAirline where personId = ip_personID;
    UPDATE person SET locationID = @locID where personId = ip_personID;

end //
delimiter ;

-- [15] recycle_crew()
-- -----------------------------------------------------------------------------
/* This stored procedure releases the assignments for a given flight crew.  The
flight must have ended, and all passengers must have disembarked. */
-- -----------------------------------------------------------------------------
drop procedure if exists recycle_crew;
delimiter //
create procedure recycle_crew (in ip_flightID varchar(50))
sp_main: begin

	if ((ip_flightID in (select flight.flightID from flight 
							inner join route_path on flight.routeID = route_path.routeID 
							where flight.progress = route_path.sequence and flight.airplane_status = 'on_ground'))
	and (select count(passenger.personID) from flight 
			inner join airplane on support_tail = tail_num and support_airline = airlineID
			inner join person on airplane.locationID = person.locationID
            inner join passenger on passenger.personID = person.personID 
            where flight.flightID = ip_flightID) = 0)
	then

	update person
    set locationID = (select a.locationID from flight as f
						join airplane as air on air.airlineID = f.support_airline and air.tail_num = f.support_tail
						join route_path as rp on rp.routeID = f.routeID
                        join leg as l on l.legID = rp.legID
                        join airport as a on l.arrival = a.airportID
                        where f.progress = rp.sequence and f.flightID = ip_flightID)
	where personID in (select personID from pilot as p
							join flight as f on f.support_tail = p.flying_tail and f.support_airline = p.flying_airline
                            where f.flightID = ip_flightID);



	update pilot
	set flying_tail = null, flying_airline = null
	where (flying_airline, flying_tail) = (select support_airline, support_tail from flight where flightID = ip_flightID);
    end if;

end //
delimiter ;


-- [16] retire_flight()
-- -----------------------------------------------------------------------------
/* This stored procedure removes a flight that has ended from the system.  The
flight must be on the ground, and either be at the start its route, or at the
end of its route.  */
-- -----------------------------------------------------------------------------
drop procedure if exists retire_flight;
delimiter //
create procedure retire_flight (in ip_flightID varchar(50))
sp_main: begin

	set @max_progress := (select count(*) from flight where flightID = ip_flightID) - 1;
    
    set @start_route := (select flight.routeID from flight
							where flightID = ip_flightID and progress = 0);
                            
	set @end_route := (select flight.routeID from flight
							where flightID = ip_flightID and progress = @max_progress);
						
	delete
    from flight
    where ((flight.routeID = @start_route) or (flight.routeID = @end_route)) and flight.airplane_status = 'on_ground';   

end //
delimiter ;

-- [17] remove_passenger_role()
-- -----------------------------------------------------------------------------
/* This stored procedure removes the passenger role from person.  The passenger
must be on the ground at the time; and, if they are on a flight, then they must
disembark the flight at the current airport.  If the person had both a pilot role
and a passenger role, then the person and pilot role data should not be affected.
If the person only had a passenger role, then all associated person data must be
removed as well. */
-- -----------------------------------------------------------------------------
drop procedure if exists remove_passenger_role;
delimiter //
create procedure remove_passenger_role (in ip_personID varchar(50))
sp_main: begin

	if ip_personID not in (select personID from person) then leave sp_main; end if;
    
    set @locID = (select locationID from person where personID = ip_personID);
    
    if @locID in (select locationID from airplane) then
		select tail_num, airlineID into @fT, @fAirline from airplane where locationID = @locID;
        if (select airplane_status from flight where support_tail = @fT and support_airline = @fAirline) = 'in_flight'
			then leave sp_main;
		end if;
	end if;
    
    DELETE FROM passenger WHERE personID = ip_personID;
    
    if ip_personID in (select personID from pilot) then leave sp_main; end if;
    
    DELETE FROM ticket_seats WHERE ticket_seats.ticketID in (select ticketID from ticket where customer = ip_personID);
    DELETE FROM ticket WHERE customer = ip_personID;
	DELETE FROM person WHERE personID = ip_personID;
end //
delimiter ;


-- [18] remove_pilot_role()
-- -----------------------------------------------------------------------------
/* This stored procedure removes the pilot role from person.  The pilot must not
be assigned to a flight; or, if they are assigned to a flight, then that flight
must either be at the start or end of its route.  If the person had both a pilot
role and a passenger role, then the person and passenger role data should not be
affected.  If the person only had a pilot role, then all associated person data
must be removed as well. */
-- -----------------------------------------------------------------------------
drop procedure if exists remove_pilot_role;
delimiter //
create procedure remove_pilot_role (in ip_personID varchar(50))
sp_main: begin

	if ip_personID not in (select personID from person) then leave sp_main; end if;
    
    set @locID = (select locationID from person where personID = ip_personID);
    
    if @locID in (select locationID from airplane) then
		select tail_num, airlineID into @fT, @fAirline from airplane where locationID = @locID;
        if (select airplane_status from flight where support_tail = @fT and support_airline = @fAirline) = 'in_flight'
			then leave sp_main;
		end if;
        select routeID, progress into @rID, @prog_num from flight where support_tail = @fT and support_airline = @fAirline;
        SET @max_seq = (select max(sequence) from route_path where routeID = @rID);
        if @prog_num != 0 and @prog_num != @max_seq then leave sp_main; end if;
	end if;
    
    DELETE FROM pilot_licenses WHERE personID = ip_personID;
	DELETE FROM pilot WHERE personID = ip_personID; 
    
    if ip_personID in (select personID from passenger) then leave sp_main; end if; 
    
    DELETE FROM ticket_seats WHERE ticket_seats.ticketID in (select ticketID from ticket where customer = ip_personID);
    DELETE FROM ticket WHERE customer = ip_personID;
	DELETE FROM person WHERE personID = ip_personID;

end //
delimiter ;


-- [19] flights_in_the_air()
-- -----------------------------------------------------------------------------
/* This view describes where flights that are currently airborne are located. */
-- -----------------------------------------------------------------------------
create or replace view flights_in_the_air (departing_from, arriving_at, num_flights,
   flight_list, earliest_arrival, latest_arrival, airplane_list) as
select l.departure as departing_from,
		l.arrival as arriving_at,
		count(f.flightID) as num_flights,
		group_concat(f.flightID order by flightID) as flight_list,
        min(f.next_time) as earliest_arrival,
        max(f.next_time) as latest_arrival,
		group_concat(a.locationID order by a.locationID) as airplane_list
        from flight as f
		inner join airplane as a on a.airlineID = f.support_airline and a.tail_num = f.support_tail
		inner join route_path as rp on rp.routeID = f.routeID and rp.sequence = (f.progress)
		inner join leg as l on l.legID = rp.legID
        where f.airplane_status = 'in_flight'
		group by l.departure, l.arrival;


-- [20] flights_on_the_ground()
-- -----------------------------------------------------------------------------
/* This view describes where flights that are currently on the ground are located. */
-- -----------------------------------------------------------------------------
create or replace view flights_on_the_ground (departing_from, num_flights,
   flight_list, earliest_arrival, latest_arrival, airplane_list) as 
select l.arrival as departing_from,
		count(distinct f.flightID) as num_flight,
		group_concat(distinct f.flightID order by flightID) as flight_list,
		min(f.next_time) as earliest_arrival,
		max(f.next_time) as latest_arrival,
		group_concat(distinct a.locationID order by a.locationID) as airplane_list
		from flight as f
		inner join airplane as a on a.airlineID = f.support_airline and a.tail_num = f.support_tail
		inner join route_path as rp on rp.routeID = f.routeID and f.progress = rp.sequence
		inner join leg as l on l.legID = rp.legID
		where f.airplane_status = 'on_ground'
		group by l.arrival
        
		union
        
		select l.departure as departing_from,
		count(distinct f.flightID) as num_flight,
		group_concat(distinct f.flightID order by flightID) as flight_list,
		min(f.next_time) as earliest_arrival,
		max(f.next_time) as latest_arrival,
		group_concat(distinct a.locationID order by a.locationID) as airplane_list
		from flight as f
		inner join airplane as a on a.airlineID = f.support_airline and a.tail_num = f.support_tail
		inner join route_path as rp on rp.routeID = f.routeID and rp.sequence = f.progress + 1
		inner join leg as l on l.legID = rp.legID
		where f.airplane_status = 'on_ground'
		group by l.departure;



-- [21] people_in_the_air()
-- -----------------------------------------------------------------------------
/* This view describes where people who are currently airborne are located. */
-- -----------------------------------------------------------------------------
create or replace view people_in_the_air (departing_from, arriving_at, num_airplanes,
   airplane_list, flight_list, earliest_arrival, latest_arrival, num_pilots,
   num_passengers, joint_pilots_passengers, person_list) as
select l.departure as departing_from,
		l.arrival as arriving_at,
		count(distinct f.flightID) as num_airplanes,
		group_concat(distinct a.locationID order by a.locationID) as airplane_list,
		group_concat(distinct f.flightID order by flightID) as flight_list,
		min(f.next_time) as earliest_arrival,
		max(f.next_time) as latest_arrival,
		sum(case when exists (select 1 from pilot where pilot.personID = p.personID) then 1 else 0 end) as num_pilots,
		sum(case when exists (select 1 from passenger where passenger.personID = p.personID) then 1 else 0 end) as num_passengers,
		count(personID) as joint_pilots_passengers,
		group_concat(personID order by personID) as person_list
		from flight as f
		inner join airplane as a on a.airlineID = f.support_airline and a.tail_num = f.support_tail
		inner join route_path as rp on rp.routeID = f.routeID and rp.sequence = (f.progress)
		inner join leg as l on l.legID = rp.legID
		inner join person p on p.locationID = a.locationID
		where f.airplane_status = 'in_flight'
		group by l.departure, l.arrival;

-- [22] people_on_the_ground()
-- -----------------------------------------------------------------------------
/* This view describes where people who are currently on the ground are located. */
-- -----------------------------------------------------------------------------
create or replace view people_on_the_ground (departing_from, airport, airport_name,
   city, state, num_pilots, num_passengers, joint_pilots_passengers, person_list) as
select a.airportID as departing_from,
		a.locationID as airport,
		a.airport_name, 
		a.city, 
		a.state, 
		sum(case when exists (select 1 from pilot where pilot.personID = p.personID) then 1 else 0 end) as num_pilots,
		sum(case when exists (select 1 from passenger where passenger.personID = p.personID) then 1 else 0 end) as num_passengers,
		count(personID) as joint_pilots_passengers,
		group_concat(personID order by personID) as person_list
		from airport a
		inner join person p on p.locationID = a.locationID
		-- where f.airplane_status = 'on_ground'
		group by a.airportID, a.locationID, a.airport_name, a.city, a.state;

-- [23] route_summary()
-- -----------------------------------------------------------------------------
/* This view describes how the routes are being utilized by different flights. */
-- -----------------------------------------------------------------------------
create or replace view route_summary (route, num_legs, leg_sequence, route_length,
	num_flights, flight_list, airport_sequence) as
-- select null, 0, null, 0, 0, null, null;
select rp.routeID as route, count(distinct rp.legID) as num_legs,
		group_concat(distinct rp.legID order by rp.sequence) as leg_sequence,
        (select sum(leg.distance) as route_length
			from leg
            where leg.legID in (select distinct leg.legID from leg
								join route_path as my_rp on my_rp.legID = leg.legID
                                where my_rp.routeID = rp.routeID)),
        
        -- sum(l.distance in (select distance from leg where distinct)) as route_length,
        count(distinct f.flightID) as num_flights,
        group_concat(distinct f.flightID order by f.flightID) as flight_list,
        group_concat(distinct concat(l.departure, '->', l.arrival) order by rp.sequence) as airport_sequence
        -- group
        from route_path as rp
        left join flight as f on f.routeID = rp.routeID
        left join leg as l on l.legID = rp.legID
        group by rp.routeID;
-- [24] alternative_airports()
-- -----------------------------------------------------------------------------
/* This view displays airports that share the same city and state. */
-- -----------------------------------------------------------------------------
create or replace view alternative_airports (city, state, num_airports,
	airport_code_list, airport_name_list) as
-- select city, state, count(*) as num_airports,
-- 		group_concat(airportID) as airport_code_list,
--         group_concat(airport_name) as airport_name_list
--         from airport
--         group by state, city
--         having count(*) >= 2;

select city, state, count(*) as num_airports,
		group_concat(airportID order by airportID) as airport_code_list,
        group_concat(airport_name order by airportID) as airport_name_list
        from airport
        group by state, city
        having count(*) >= 2;

-- [25] simulation_cycle()
-- -----------------------------------------------------------------------------
/* This stored procedure executes the next step in the simulation cycle.  The flight
with the smallest next time in chronological order must be identified and selected.
If multiple flights have the same time, then flights that are landing should be
preferred over flights that are taking off.  Similarly, flights with the lowest
identifier in alphabetical order should also be preferred.

If an airplane is in flight and waiting to land, then the flight should be allowed
to land, passengers allowed to disembark, and the time advanced by one hour until
the next takeoff to allow for preparations.

If an airplane is on the ground and waiting to takeoff, then the passengers should
be allowed to board, and the time should be advanced to represent when the airplane
will land at its next location based on the leg distance and airplane speed.

If an airplane is on the ground and has reached the end of its route, then the
flight crew should be recycled to allow rest, and the flight itself should be
retired from the system. */
-- -----------------------------------------------------------------------------
drop procedure if exists simulation_cycle;
delimiter //
create procedure simulation_cycle ()
sp_main: begin

	set @fastest_flight := null;
    
    select flightID into @fastest_flight 
    from flight
    where (next_time is not null) and (airplane_status is not null)
    order by next_time ASC, airplane_status desc, flightID asc
    limit 1;
    
    set @next_status := (select airplane_status from flight where flightID = @fastest_flight);
    set @next_flight_time := (select next_time from flight where flightID = @fastest_flight);
    
    if (@next_status = 'in_flight')
    then
    call flight_landing(@fastest_flight);
    elseif (@next_status = 'on_ground')
    then
    call flight_takeoff(@fastest_flight);
    end if;
    
    call retire_flight(@fastest_flight);
    
end //
delimiter ;
