Scheduler Application

Application for dealing with schedule tasks using Java/Spring Boot/Angular/H2. Application:
• Provide CRUD functionality for Scheduler Tasks
• Schedule Task should be:
o automatically triggered based on the recurrency
o executed in separate Thread
o execute Code in Groovy Schell
Main requirements are:
• Application must be single page app communicating via API to backend service
• User should be able to:
o Create, Update and Delete Schedule Task
o See the list existing Schedule Tasks in a table
• Form for editing Schedule Task should contain three fields:
o Name - String (250 characters) - input field
o Recurrency - String (30 characters) - input field, value in cron format
o Code – String (Clob) - text field, value will represent Groovy Script
• Scheduler Job Executor
o Should be responsible for executing Schedule Tasks based on cron value using Thread from the pool
• Output should be visible in the console log
This development practices and extra features will be considered as a plus:
• Develop application using TDD – Unit Tests
• Validation of input data
• Docker containerization
Example of Groovy script:
for(1..10){
println "Hello World"
sleep(1000)
}
