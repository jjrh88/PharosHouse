var exec = require('cordova/exec');


module.exports.capturar = function (arg0, success, error) {
 exec(success, error, 'PharosHouse', 'capturar', [arg0]);
};
