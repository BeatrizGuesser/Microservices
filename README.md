# Microservices Project
This is a microservices project that aims to create a platform to manage cars, races and race history. The project consists of three microservices: ms-cars, ms-races and ms-history. Each microservice has specific functionalities and integrations with MongoDB, OpenFeign and RabbitMQ.
# Built with 
Java, Spring Boot, MongoDB, OpenFeign, RabbitMQ, Postman Collection and use of the docker-compose.yml file.
# MS-CARS Microservice Rules:
• There cannot be pilots who are completely the same.

• Implementation of CRUD (Create, Read, Update, Delete) for cars.

• Ensure that no cars are completely the same.
# Example JSON structure of a car:
}

    "car": {
    
        "brand": "String",
        
        "model": "String",
        
        "pilot": {
        
            "name": "String",
            
            "age": Integer
            
        }, 
        
        "year": "Date"
}
# MS-CARS EndPoints (http://localhost:8080):
• POST: http://localhost:8080/api/v1/cars/post

• GET (get all): http://localhost:8080/api/v1/cars/get

• GET (get by id): http://localhost:8080/api/v1/cars/get/{id}

• PUT: http://localhost:8080/api/v1/cars/update/{id}

• DELETE: http://localhost:8080/api/v1/cars/delete/{id}

• GET (get 10 cars): http://localhost:8080/api/v1/cars/get/top10

# MS-RACES Microservice Rules:
• Consume the ms-cars microservice to obtain a maximum of 10 cars.

• The application must randomly select 3 to 10 cars.

• A car can only overtake the car in front of it.

• After finishing a race you can no longer update a race or overtake a car.

• Send the race result to a RabbitMQ queue.
# Example JSON structure of a race:
{

    "name": "String",
    
    "country": "String",
    
    "date": "Date"
    
}
# MS-RACES EndPoints (http://localhost:8081):
• POST: http://localhost:8081/api/v1/races/post

• GET (get all): http://localhost:8081/api/v1/races/get

• GET (get by id): http://localhost:8081/api/v1/races/get/{id}

• PUT: http://localhost:8081/api/v1/races/update/{id}

• DELETE: http://localhost:8081/api/v1/races/delete/{id}

• PUT (overtake a car): http://localhost:8081/api/v1/races/{idRace}/overtake/{carId}/{carToOvertakeId}

• PUT (finish a race): http://localhost:8081/api/v1/races/finish/{id}

• GET (get 10 cars): http://localhost:8081/api/v1/races/get10
# MS-HISTORY Microservice Rules:
• Consume the ms-races microservice queue and save it to the database.

• Include the date the record was entered into the database.

• Provide an endpoint to query the race by ID.

• Provide an endpoint to query all races.
# MS-HISTORYS EndPoints (http://localhost:8082):
• GET (get all): http://localhost:8082/api/v1/races/history/get

• GET (get by id): http://localhost:8082/api/v1/races/history/get/{id}
# Branches:
• Two fixed branches were used: main and dev. I added separate branches for each microservice and also a separate branch for testing.
# Quality:
• 40% test coverage, at least.

• NOTE: the tests showed errors when running with Docker, I will continue to work on the project to fix this problem and I will also add security as time passes.
