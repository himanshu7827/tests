(function(){

	/**
	 * To Escape the Regular Expressions for normal user supplied special characters
	 */
	RegExp.quote = function( str ){
		return (str+'').replace(/([.?^*+$[\]\\(){}|-])/g,"\\$1");
	}

	/**
	 * equals		: Compares value with some other field
	 * USAGE			: equals[\"#otherField\"] (Argument is ID of field to compare)
	 * Precaution	: The string passed must be escaped.
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   equals: {  
			validator: function(value,param){  
			   return value === $(param[0]).val();  
			},  
	      message: 'Both passwords do not match.'  
	   }  
	});

	/**
	 * Alpha		: Validates Alphabets
	 * USAGE		: Alpha
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   Alpha: {
	      validator: function(value){
	      	value = $.trim(value);
	        	var r = /^[A-Z\\s ]+$/i;
            return value.match(r);
        	},  
	      message: 'Alphabets only.'
	   }  
	});

	
	
	
	
	$.extend($.fn.validatebox.defaults.rules, {  
		   UIDLength: {
				validator: function(value){
					return value.length == 14;  
				},  
				message: 'UID length should be 12 digits.'
		   }  
		});
	
		
	/**
	 * CheckZero	: Validates that fields do not have all zeroes
	 * USAGE			: CheckZero[\" arg \"] (arg can be int/float)
	 *	INFO			: The messages are based on arg i.e. int or float
	 * Precaution	: The string passed must be escaped.
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   CheckZero: {
	      validator: function(value,param){
	      	value = $.trim(value);
	      	if(param[0].toLowerCase() === 'float'){
	      		param[1] = 'Cannot start with Zero';
	      		return (parseFloat(value) !== 0);
	      	} else {	
	      		param[1] = 'Cannot be Zero';
	      		return (parseInt(value) !== 0);
	      	}
        	},  
	      message: '{1}'
	   }  
	});

	/**
	 * Numeric		: Validates Numbers
	 * USAGE			: Numeric
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   Numeric: {
	      validator: function(value){
	      	value = $.trim(value);
	        	var r = /^[0-9]+$/i;
            return value.match(r);
        	},  
	      message: 'Numbers only.'
	   }  
	});
	
	$.extend($.fn.validatebox.defaults.rules, {  
		   NumericHyphen: {
		      validator: function(value){
		      	value = $.trim(value);
		        	var r = /^[0-9-]+$/i;
	            return value.match(r);
	        	},  
		      message: 'Numbers and - only( e.g 12-90) .'
		   }  
		});
	
	
	
	$.extend($.fn.validatebox.defaults.rules, {  
		   NumberOnly: {
		      validator: function(value){
		        	var r = /^[0-9]+$/i;
	            return value.match(r);
	        	},  
		      message: 'Numbers only.'
		   }  
		});
	
	/**
	 * Numeric		: Validates Numbers
	 * USAGE			: Numeric
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   NumericContact: {
	      validator: function(value){
	      	value = $.trim(value);
	        	var r = /^[0-9-,]+$/i;
            return value.match(r);
        	},  
	      message: 'Numbers with - and , only.'
	   }  
	});
	

	/**
	 * NumericRange		: Validates Numbers
	 * USAGE			: NumericRange[ min,max ] i.e (0,10 )
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   NumericRange: {
	      validator: function(value,param){
	      	value = $.trim(value);
	      
	      	if(value >= param[0] && value <= param[1]){
	      	
	      		return true;
	      	}
	      	else
	      		{
	      		param[2] = 'Value should be between ' + param[0] + ' and ' + param[1] + '.';
	      		return false;
	      		}
	      	},
	      	 message:  '{2}'
        	}  
	     
	    
	});
	
	/**
	 * NumericRange		: Validates Numbers
	 * USAGE			: NumericRange[ min,max ] i.e (0,10 )
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   DateYearRange: {
	      validator: function(value,param){
	      	value = $.trim(value);
	      var currentYear = param[0];
	    //  alert(value);
	     // alert(currentYear);
	      var validYear = currentYear-param[1];
	      //alert(validYear);
	      	if(value > currentYear || value < validYear){
	      	//alert("in if");
	      		param[2] = 'Year should be between ' + validYear + ' and ' + currentYear + '.';
	      		return false;
	      	}
	      	else
	      		{
	      		
	      		return true;
	      		}
	      	},
	      	 message:  '{2}'
        	}  
	     
	    
	});

	/**
	 * Decimal		: Validates Decimal Nos
	 * USAGE			: Decimal[ len,len ] i.e (3,4 => 123.1234)
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   Decimal: {
	      validator: function(value,param){
	      	value = $.trim(value);

	      	var numbersOnly = /^[0-9\.]+$/;

	      	if(numbersOnly.test(value)){
	      		var period = value.indexOf('.');

		      	//Check if more periods exist
		      	if(value.indexOf('.',period+1) > -1){
		      		param[2] = 'Invalid Decimal Number.';
		      		return false;
		      	}

		      	if(period > 0){
		      		beforePeriod = value.substr(0,period);
		      		afterPeriod = value.substr(period+1);
		      		if(beforePeriod.length > param[0]) {
		      			param[2] = 'Only '+ param[0] +' digit(s) before period.';
		      			return false;
		      		} else if(afterPeriod.length > param[1]) {
		      			param[2] = 'Only '+ param[1] +' digit(s) after period.';
		      			return false;
		      		} else {
		      			return true;
		      		}
		      	} else if( period === 0) {
		      		param[2] = 'Value needed before period.';
		      		return false;
		      	} else if( period === -1 ){
		      		if(value.length > param[0]){
		      			param[2] = param[0] + ' digit(s) only.';
		      			return false;
		      		} else {
		      			return true;
		      		}
		      	} else {
		      		return true;
		      	}
	      	} else {
	      		param[2] = 'Invalid Decimal Number.';
	      		return false;
	      	}
        	},  
	      message: '{2}'
	   }  
	});

	/**
	 * AlphaWithApos	: Validates Alphabets and Apostrophe [a-zA-Z' ]
	 * USAGE				: AlphaWithApos
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   AlphaWithApos: {
	      validator: function(value){
	      	value = $.trim(value);
	        	var r = /^[A-Z'.]+$/i;
            return value.match(r);
        	},  
	      message: 'Alphabets &amp; Apostrophe only.'
	   }  
	});
	
	
	$.extend($.fn.validatebox.defaults.rules, {  
		   AlphaWithOutApos: {
		      validator: function(value){
		      	value = $.trim(value);
		        	var r = /^[A-Z.]+$/i;
	            return value.match(r);
	        	},  
		      message: 'Alphabets only.'
		   }  
	});

	/**
	 * AlphaNumeric	: Validates Alphabets and Numbers [a-zA-Z0-9 ]
	 * USAGE				: AlphaNumeric
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   AlphaNumeric: {
	      validator: function(value){
	      	value = $.trim(value);
        	var r = /^[A-Z0-9-',.!@#$&*()"// ]+$/i;
            return value.match(r);
        	}, 
		      message: 'Alphabets &amp; Numbers only.'

	   }  
	});

	/**
	 * Added by Fazia for RTGS details
	 * AlphaNumeric	: Validates Alphabets and Numbers [a-zA-Z0-9 ]
	 * USAGE				: AlphaNumericNoApos
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
		   AlphaNumericNoApos: {
		      validator: function(value){
		      	value = $.trim(value);
	        	var r = /^[A-Z0-9-,// ]+$/i;
	            return value.match(r);
	        	}, 
			      message: 'Alphabets, Numbers, comma &amp; / only.'

		   }  
		});
	
	$.extend($.fn.validatebox.defaults.rules, {  
		AlphaNumericWithSpecial: {
		      validator: function(value){
		      	value = $.trim(value);
                  var r=/[<\>]/;
	            return !value.match(r);
	            
	        	},  
	  	      message: 'Special Characters > and < are not allowed.'

		   }  
		});
	
	
	$.extend($.fn.validatebox.defaults.rules, {  
		AlphaNumericWithSpecialforKeywords: {
		      validator: function(value){
		      	value = $.trim(value);
		         var r = /^[A-Z0-9'\-,.!@#$&*()"%^_\\+={}\/[\]?~`:;| ]+$/i;
	            return value.match(r);
	            
	        	},  
	  	      message: 'Special Characters <,> and "Enter" are not allowed.'

		   }  
		});

	/**
	 * AlphaNumericWithApos	: Validates Alphabets,Numbers and Apostrophe [a-zA-Z0-9' ]
	 * USAGE						: AlphaNumericWithApos
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   AlphaNumericWithApos: {
	      validator: function(value){
	      	value = $.trim(value);
	        	var r = /^[A-Z0-9' ]+$/i;
            return value.match(r);
        	},  
	      message: 'Alphabets, Numbers &amp; Apostrophe only.'
	   }  
	});

	/**
	 * AlphaSpecial	: Validates Alphabets and Characters Passed as a String
	 * USAGE				: AlphaSpecial[\"string\"]
	 * Precaution		: The string passed must be escaped.
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   AlphaSpecial: {
	      validator: function(value, param){
	      	value = $.trim(value);
	        	var t = "^[A-Z" + RegExp.quote(param[0]) + " ]+$";
	        	var z = new RegExp(t,'i');
	        	param[1] = param[0].replace(/(.)/g,', $1').substr(1);
            return value.match(z);
        	},  
	      message: 'Alphabets &amp; {1} only.'
	   }  
	});

	/**
	 * minLength		: Validates minimum no of characters 
	 * USAGE				: minLength[ length ]
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   minLength: {
			validator: function(value,param){
				return value.length >= param[0];  
			},  
			message: 'Enter at Least {0} characters.'
	   }  
	});

	/**
	 * maxLength		: Validates Maximum no of characters 
	 * USAGE				: maxLength[ length ]
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   maxLength: {
			validator: function(value,param){
				return value.length <= param[0];  
			},  
			message: 'Enter at Most {0} characters.'
	   }  
	});

	/**
	 * Mobile	: Validates Mobile nos [ (+919911223344) OR (9911223344) ]
	 * USAGE		: Mobile
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   Mobile: {
	      validator: function(value){
	      	value = $.trim(value);
	      	var r = /^([1-9])([0-9]{10,14})$/;
	        	/*var r = /^(\+[1-9]{2})?[0-9]{12}$/;*/
	            if(value=="000000000000000") {
                return false;
                }
            return value.match(r);
        	},  
	      message: 'Invalid Mobile Number.'
	   }  
	});

	// added by Parmita
	$.extend($.fn.validatebox.defaults.rules, {  
		   PasswordCheck: {
		      validator: function(value){
		      	value = $.trim(value);
		        var r= /^(?=.*[a-z,A-Z])(?=.*[0-9])/;
		        return value.match(r);
	        	},  
		      message: 'Password should contain atleast one letter and one number.'
		   }  
		});
	//end
	
	/**
	 * NumericSpace		: Validates Numbers and space
	 * USAGE			: Numeric and space
	 */
	$.extend($.fn.validatebox.defaults.rules, {  
	   NumericSpace: {
	      validator: function(value){
	      	value = $.trim(value);
	        	var r = /^[0-9\S]+$/i;
            return value.match(r);
        	},  
	      message: 'Numbers with space only.'
	   }  
	});
	
	
	$.extend($.fn.validatebox.defaults.rules, {  
		   NumberGreaterThanZeroOnly: {
		      validator: function(value){
		        	var r = /^[0-9]+$/i;
		        	if(value>0)
		        	{
		        		return value.match(r);
		        	}
		       },  
		      message: 'Value should be numeric and greater than zero only.'
		   }  
		});
	
	
}());
