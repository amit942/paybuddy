console.log("entered in script");
$(document).ready(function() {
      $("#paymentMethod input:radio").click(function() {
           console.log("clicked");
      });
      
      $("#paymentMethod  #ydp_radio_option").click(function() {
           console.log("clicked ydp");
           $("#ydp_info").show();
     });
      
     $("#ydp_send_message").click(function() {
     $("#display_msg").show();
	 var input = $("#ydp_input_text").val();
	
	 // assuming email only for now.
	if(input === undefined || input == "") {
		$("#display_msg").html("enter valid email");
		$("#display_msg").show();	     
	}else {

		$.ajax({
			url: 'payment/msg?messageSendType=email&payerMobile=&payerEmail='+input+'&txnid=123456789&amount=1234&productInfo=product1&firstName=name&email=dudani.priyanka',
            dataType: 'json',
            type: 'GET',
            contentType: 'application/json; charset=utf-8',
            success: function(data){
                	console.log("success ajax" + data);
            },
            error: function(data){
                    console.log("ajax error" + data);
                }
            });
		}
      });
     
     $("#ydp_payment_do").click(function() {
     	
     	var txnid = $('#ydp_txnid');
     	var link = $('#ydp_link');
     	$.ajax({
     		url: 'payment/do?txnid='+txnid+'&link='+link,
             dataType: 'json',
             type: 'GET',
             contentType: 'application/json; charset=utf-8',
             success: function(data){
                 	console.log("success ajax" + data);
             },
             error: function(data){
                     console.log("ajax error" + data);
             }
             });
     	});
});


