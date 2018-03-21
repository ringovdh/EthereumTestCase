pragma solidity ^0.4.0;

contract healthCheck {
    
    /* unieke id voor de referentie in onze app */
    string patientID;
    /* hash van het totale dossier op het moment van de laatste wijzigingen */
    string dossierStatus;
    
    function healthCheck(string _patientID, string _dossierStatus) public {
        patientID = _patientID;
        dossierStatus = _dossierStatus;
    }
    
    function setDossierStatus(string _dossierStatus) public {
        dossierStatus = _dossierStatus;
    }
    
    function getDossierstatus() public constant returns (string) {
        return dossierStatus;
    }
    
}