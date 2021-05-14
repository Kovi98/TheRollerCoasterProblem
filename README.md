# The Roller Coaster Problem

According to [Little Book Of Semaphores](https://greenteapress.com/semaphores/LittleBookOfSemaphores.pdf):

Suppose there are `n` passenger threads, and a car thread. The passengers repeatedly wait to take rides
in the car, which can hold `C` passengers, where `C < n`. The car can go around the tracks only
when it is full. *(I altered this rule to work for any number because making it to only work with a full
car was kinda boring, ngl.)*

Here are some additional details:
* passengers should invoke `board` and `unboard`.
* the car should invoke `load`, `run` and `unload`;
* passengers cannot board until the car has invoked `load`;
* the car cannot depart until `C` passengers have boarded;
* passengers cannot unboard until the car has invoked `unload`.

Puzzle: Write code for the passengers and car that enforces these constraints.