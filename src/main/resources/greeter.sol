pragma solidity ^0.4.0;

contract greeter {
    
    string greeting;
    
    function greeter(string _greeting) public {
        greeting = _greeting;
    }
    
    function setGreeting(string _greeting) public {
        greeting = _greeting;
    }
    
    function greet() public constant returns (string) {
        return greeting;
    }

}