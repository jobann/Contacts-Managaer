'use strict';
const fs = require('fs');

var express = require('express'); 
var app = express(); 
 
const bodyParser = require('body-parser'); 
var server = app.listen(3000); 
 
app.use(bodyParser.json()); 
app.use(bodyParser.urlencoded({ extended: true })); 

app.get('/', function (req, res) {
  let rawdata = JSON.parse(fs.readFileSync('contacts.json', 'utf8'));
   let data = JSON.stringify(rawdata);console.log();
  res.send(data.substring(1, data.length-1));
}); 
 
app.post('/', (req, res) => { 
	var result = JSON.parse(fs.readFileSync('contacts.json', 'utf8'));
	var data = req.body; // your data 
    // do something with that data (write to a DB, for instance)
  
    var ts = Date.now().toString()
	
	result.push({data});
	
	let jsonData = JSON.stringify(result);	
	
	fs.writeFile('contacts.json', jsonData, (err) => {
    if (err) throw err;
    console.log('Data written to file');
});
	
res.status(200).json({ 
	message: "Contact saved successfully!" 
}); 
}); 

app.listen(0, () => console.log('Application is running'));