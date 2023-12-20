create table center (
id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
name varchar(255),
address varchar(255)
)

create table users (
id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
email varchar(255),
password varchar(255)
)

create table fresher (
id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
name varchar(255),
email varchar(255),
programming_language varchar(255),
score_1 numeric(3,1),
score_2 numeric(3,1),
score_3 numeric(3,1),
center_id int,
CONSTRAINT fk_fresher_center
FOREIGN KEY (center_id) 
REFERENCES center(id)
)

insert into center(name,address)
values 
('Ton That Thuyet', '18 Ton That Thuyet'),
('Duy Tan', '21 Duy Tan')

insert into users(email,password)
values 
('test@gmail.com', '$2a$10$bInjvzAV97DXBjzUB4bKeus9BwQqYGCcZ9Q21TwN7sXvqJj/N1wUy')

insert into fresher(name,email,programming_language,score_1,score_2,score_3,center_id)
values 
('Hung', 'hungltfx18689@funix.edu.vn', 'Java', 9.0, 8.5, 9.5, 1)

drop table center
drop table users
drop table fresher

select * from refresh_token
select * from center
select * from users
select * from role
select * from fresher
select count(*) from fresher where center_id = 2

with abc as (
select cast(((score_1+score_2+score_3)/3) as decimal(3,1)) as average_core,
case 
when ((score_1+score_2+score_3)/3)>=0 and ((score_1+score_2+score_3)/3)<1 then '0-1'
when ((score_1+score_2+score_3)/3)>=1 and ((score_1+score_2+score_3)/3)<2 then '1-2'
when ((score_1+score_2+score_3)/3)>=2 and ((score_1+score_2+score_3)/3)<3 then '2-3'
when ((score_1+score_2+score_3)/3)>=3 and ((score_1+score_2+score_3)/3)<4 then '3-4'
when ((score_1+score_2+score_3)/3)>=4 and ((score_1+score_2+score_3)/3)<5 then '4-5'
when ((score_1+score_2+score_3)/3)>=5 and ((score_1+score_2+score_3)/3)<6 then '5-6'
when ((score_1+score_2+score_3)/3)>=6 and ((score_1+score_2+score_3)/3)<7 then '6-7'
when ((score_1+score_2+score_3)/3)>=7 and ((score_1+score_2+score_3)/3)<8 then '7-8'
when ((score_1+score_2+score_3)/3)>=8 and ((score_1+score_2+score_3)/3)<9 then '8-9'
when ((score_1+score_2+score_3)/3)>=9 and ((score_1+score_2+score_3)/3)<10 then '9-10'
else '10'
end as score_range
from fresher
)

select count(*), score_range
from abc
group by score_range
