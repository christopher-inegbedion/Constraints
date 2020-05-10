# **Constraints**
##### current version: 0.1.3

Constraints are rules that have to be passed in order to continue. There are 2 main categories of constraints:
    1. Non Response-target types
    2. Response-target types
    
Non Response-target constraint types do not have a waiting period and return the result as the value they were given,
while Response-target constraint types will have a particular target/response they will be waiting to receive or reach,
during this waiting period, progress can be monitored by calling the `updates(String waiting_message)` method which
will return true if finished or display a waiting message and false if not.

Non Response-target constraint types include ReadyConstraint, ActiveConstraint
Response-target constraint types include TimeConstraint

### **Creating a Non Response-target constraint**
***

Using ReadyConstraint as an example
```java
ReadyConstraint constraint = new ReadyConstraint(true);

constraint.start(constraint.requirement);
```


Since ReadyConstraint is a Non Response-target constraint there is no update, thus the value will be returned directly
in the `response()` method.


### **Creating a Response-target constraint**
***

Using TimeConstraint as an example

```java
Date current_date = ...
Date future_date = ...

TimeConstraint constraint = new TimeConstraint(current_date, future_date);
constraint.start(constraint.requirement, constraint.required_value);
```


TimeConstraint is a Response-target constraint and thus will provide updates about the current progress from the 
`response()` method.

# **Combined constraints** 
Constraints can be combined to produce a custom set of responses. An already combined constraint can be combined with 
other constraints to create new combined constraints. 

Constraints can be combined with an AND/OR logic value modifier, depending on the requirements. 

A combined constraint is still a constraint and so thus the standard constraint methods can be used with a combined constraint.


#### Combination types
***
The available combination types are  AND being the logical AND of both the return constraint values and OR being
the logical OR of both the return constraint values.

##### Using AND
`AND.getCombination()`

##### Using OR
`OR.getCombination()`

### **Creating a combined constraint (from separate constraints)**
***

```java
ReadyConstraint ready_constraint1 = new ReadyConstraint(true);
ReadyConstraint ready_constraint2 = new ReadyConstraint(false);

//creating a combined constraint with 2 seperate constraints
CombinedConstraint combined_constraint = new CombinedConstraint(ready_constraint1, ready_constraint2, AND.getCombination);
combined_constraint.start();

```

Once a combined constraint has been created it can be used like any other constraint and can also be combined with another
constraint.


### **Creating a combined constraint (from a separate constraint and combined constraint)**
***

```java
ReadyConstraint ready_constraint1 = new ReadyConstraint(true);
ReadyConstraint ready_constraint2 = new ReadyConstraint(false);

//creating a combined constraint with 2 separate constraints
CombinedConstraint combined_constraint1 = new CombinedConstraint(ready_constraint1, ready_constraint2, AND.getCombination);
combined_constraint1.start(); //produces false

//creating a combined constraint with a combined constraint and separate constraint
CombinedConstraint combined_constraint2 = new CombinedConstraint(ready_constraint1, OR.getCombination);
combined_constrain2.hasCombinedConstraint(combined_constraint1)
combined_constrain2.start(); //produces true

```

# **Stages** 
A stage is a group of constraints. Each stage has a name. It differs from a combined constraint in that the final value is produced with a logical 
AND on all the constraints.

> As of version 0.1.3 only separate constraints can be added to the stage

### **Creating a stage**
```java
ReadyConstraint ready_constraint1 = new ReadyConstraint(true);
ReadyConstraint ready_constraint2 = new ReadyConstraint(false);
ReadyConstraint ready_constraint3 = new ReadyConstraint(false);

Stage stage = new Stage("Stage name");

stage.addConstraint(ready_constraint1);
stage.addConstraint(ready_constraint2);
stage.addConstraint(ready_constraint3);

stage.start();
```


That's it 🎉.

# Author
Christopher Inegbedion
