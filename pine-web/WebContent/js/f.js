function xhrSendParameter(xhrUrl, jsonParam, successFn, errorFn) {
	parent.showPleaseWait();
	$.ajax({
	    url : xhrUrl,
	    timeout: 300000,
	    dataType : 'json',
	    data : jsonParam,
	    cache: false,
	    async: true,
	    success : function(data, textStatus) {
			setTimeout(function(){
				parent.hidePleaseWait();
			}, 350);   	    	
			if (data==null || (typeof data=='undefined') ) {
				alert('Unexpected error!');
				return;
			}    			
			if ('Y'!=data.login) {
				alert("Please try login again!");
				return;
			}       
			if ('Y'!=data.isAuthorize) {
				alert("No permission!");
				return;        				
			}        						
			successFn(data);
	    },
	    error : function(jqXHR, textStatus, errorThrown) {
			setTimeout(function(){
				parent.hidePleaseWait();
			}, 350);   	    	
	        alert(textStatus);
	        errorFn();
	    }
	});
}
