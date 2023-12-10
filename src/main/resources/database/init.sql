create database telecom_db;

create table user_subscription
(
	user_id varchar not null
		constraint user_subscription_pk
			primary key,
	subscribed bool not null
);

