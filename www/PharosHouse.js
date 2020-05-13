var exec = require('cordova/exec');


exports.capturar = function (arg0, success, error) {
    exec(success, error, 'pharoshouse', 'capturar', [arg0]);
};

//var PLUGIN_NAME = 'PharosHouse';

// var PharosHouse = {
//     capturar: function (arg, successCallback, errorCallback){
//     exec(successCallback, errorCallback, PLUGIN_NAME, "capture", [arg]);
//   }
// };


///module.exports = PharosHouse;