[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c1eed60e30544fda88f9224db420896e)](https://www.codacy.com/gh/gluhov-d/restaurant-voting-application/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gluhov-d/restaurant-voting-application&amp;utm_campaign=Badge_Grade)

Restaurant voting application
===============================

Most popular technologies / tools /frameworks Java Enterprise:
Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson)

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

### curl samples.
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/api/admin/users`

#### get User 1
`curl -s http://localhost:8080/api/admin/users/1`

#### delete User 1
`curl -s -X DELETE http://localhost:8080/api/admin/users/1`

#### register User
`curl -s -i -X POST -d '{"firstName":"First Name", "lastName": "Last Name", "email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/profile`

#### get Profile User 1
`curl -s http://localhost:8080/api/profile/1`

#### get All Restaurants
`curl -s http://localhost:8080/api/restaurants`

#### get Restaurant 1
`curl -s http://localhost:8080/api/restaurants/1`

#### create Restaurant
`curl -s -X POST -d '{"id": null, "name": "New Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurants`

#### update Restaurant 1
`curl -s -X PUT -d '{"id": 1, "name": "Updated Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurants/1`

#### delete Restaurant 1
`curl -s -X DELETE http://localhost:8080/api/restaurants/1`

#### get All Dishes for Restaurant 1
`curl -s http://localhost:8080/api/restaurants/1/dishes`

#### get Dish 1 for Restaurant 1
`curl -s http://localhost:8080/api/restaurants/1/dishes/1`

#### get Dish not found for Restaurant 1
`curl -s -v http://localhost:8080/api/restaurants/1/dishes/100`

#### create Dish for Restaurant 1
`curl -s -X POST -d '{"name": "New Steak", "localDate": "2022-08-25", "price": 25000}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurants/1/dishes`

#### update Dish 1 for Restaurant 1
`curl -s -X PUT -d '{"name": "Updated Steak", "localDate": "2022-08-24", "price": 26000}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurants/1/dishes/1`

#### delete Dish 1 for Restaurant 1
`curl -s -X DELETE http://localhost:8080/api/restaurants/1/dishes/1`

#### filter Dishes for Restaurant 1
`curl -s "http://localhost:8080/api/restaurants/1/dishes/filter?startDate=2022-08-25&endDate=2022-08-28"`

#### get All Votes for User 1
`curl -s http://localhost:8080/api/profile/1/votes`

#### get Vote 1 for User 1
`curl -s http://localhost:8080/api/profile/1/votes/1`

#### get Vote not found for User 1
`curl -s -v http://localhost:8080/api/profile/1/votes/100`

#### create Vote for User 1
`curl -s -X POST -d '{"restaurant": {"id": "2", "name": "Noma"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/profile/votes --user user@yandex.ru:password`

#### update Vote 1 for User 1
`curl -s -X PUT -d '{"restaurant": {"id": "2", "name": "Noma"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/profile/votes/1 --user user@yandex.ru:password`

#### delete Vote 1 for User 1
`curl -s -X DELETE http://localhost:8080/api/profile/1/votes/1`

#### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/api/admin/users`
`curl -s -X PUT -d '{"localDate":"2022-05-30T07:00"}' -H 'Content-Type: application/json' http://localhost:8080/api/profile/1/votes/100`