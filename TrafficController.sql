CREATE TABLE `buildings` (
  `buildingid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trialid` int(11) NOT NULL,
  `buildingscol` varchar(45) DEFAULT NULL,
  `facility_id` int(11) NOT NULL,
  `facility_name` varchar(45) DEFAULT NULL,
  `xcor` int(11) DEFAULT NULL,
  `ycor` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `dest` varchar(45) DEFAULT NULL COMMENT 'ID of destination facility. Can be a single integer or a comma-separated list.  ',
  `num_workers` int(11) DEFAULT NULL,
  `slow_in_mud` binary(1) DEFAULT NULL,
  `tired_in_mud` binary(1) DEFAULT NULL,
  `injured_in_mud` binary(1) DEFAULT NULL,
  `min_time_working` int(11) DEFAULT NULL COMMENT 'Number of ticks where a worker remains inside a work building, before travelling back to the source building.',
  `max_time_working` int(11) DEFAULT NULL,
  PRIMARY KEY (`buildingid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;


CREATE TABLE `traffictrials` (
  `trialid` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL COMMENT 'This timestamp is read from the results file, so that we have a way to connect the different pieces of information.',
  `total_trips` int(11) DEFAULT '0',
  `runtime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`trialid`),
  UNIQUE KEY `trialid_UNIQUE` (`trialid`),
  UNIQUE KEY `timestamp_UNIQUE` (`timestamp`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;


CREATE TABLE `groupstats` (
  `groupid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trialid` int(11) NOT NULL,
  `source_building` int(11) DEFAULT NULL,
  `dest_building` int(11) DEFAULT NULL,
  `number_avoiders` int(11) DEFAULT NULL,
  `number_bold` int(11) DEFAULT NULL,
  `avg_trips_avoiders` float DEFAULT NULL,
  `avg_trips_bold` float DEFAULT NULL,
  `avg_trip_time_avoider` float DEFAULT NULL,
  `avg_trip_time_bold` float DEFAULT NULL,
  `avg_bold_tired_ticks` float DEFAULT NULL COMMENT 'Of the workers in this group who go through mud, what is the average time that they become tired and stop.',
  `injured_workers` int(11) DEFAULT NULL COMMENT 'How many workers in this group were injured.',
  `total_trips` int(11) DEFAULT NULL,
  `total_bold_trips` int(11) DEFAULT NULL,
  `total_avoider_trips` int(11) DEFAULT NULL,
  PRIMARY KEY (`groupid`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
