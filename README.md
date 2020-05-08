# **Constraints**

Constraints are rules that have to be passed in order to continue. There are 2 main categories of constraints:
    1. Non Response-target types
    2. Response-target types
    
Non Response-target constraint types do not have a waiting period and return the result as the value they were given,
while Response-target constraint types will have a particular target/response they will be waiting to receive or reach,
during this waiting period, progress can be monitored by calling the `updates(String waiting_message)` method which
will return true if finished or display a waiting message and false if not.

Non Response-target constraint types include ReadyConstraint, ActiveConstraint
Response-target constraint types include TimeConstraint

### **Starting a Non Response-target constraint**
***

Using ReadyConstraint as an example
```
ReadyConstraint constraint = new ReadyConstraint(true);

constraint.start(constraint.requirement);
```


Since ReadyConstraint is a Non Response-target constraint there is no update, thus the value will be returned directly
in the `response()` method.


## **Starting a Response-target constraint**
***

Using TimeConstraint as an example

```
Date current_date = ...
Date future_date = ...

TimeConstraint constraint = new TimeConstraint(current_date, future_date);
constraint.start(constraint.requirement, constraint.required_value);
```


TimeConstraint is a Response-target constraint and thus will provide updates about the current progress from the 
`response()` method.


That's it 🎉.
##### _current version: 0.1.3_
