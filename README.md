[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c1eed60e30544fda88f9224db420896e)](https://www.codacy.com/gh/gluhov-d/restaurant-userVote-application/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gluhov-d/restaurant-userVote-application&amp;utm_campaign=Badge_Grade)

Restaurant voting application
===============================

Most popular technologies / tools /frameworks Java Enterprise:
Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson)

Download executable application https://disk.yandex.ru/d/47Z4TF4uvZTVYw

To test application use swagger at http://localhost:8080/swagger-ui/index.html

##  Technical requirement:
Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it (**better - link to Swagger**).

-----------------------------
P.S.: Make sure everything works with latest version that is on github :)  
P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.