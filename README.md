Scheduler Application

Application for dealing with schedule tasks using Java/Spring Boot/Angular/H2. Application:

• Provide CRUD functionality for Scheduler Tasks

• Schedule Task should be:

- automatically triggered based on the recurrency

- executed in separate Thread

- execute Code in Groovy Schell

Main requirements are:

• Application must be single page app communicating via API to backend service

• User should be able to:
- Create, Update and Delete Schedule Task
- See the list existing Schedule Tasks in a table

• Form for editing Schedule Task should contain three fields:
- Name - String (250 characters) - input field
- Recurrency - String (30 characters) - input field, value in cron format
- Code – String (Clob) - text field, value will represent Groovy Script

• Scheduler Job Executor
- Should be responsible for executing Schedule Tasks based on cron value using Thread from the pool

• Output should be visible in the console log


Example of Groovy script:

for(1..10){

  println "Hello World"

  sleep(1000)
  
}
