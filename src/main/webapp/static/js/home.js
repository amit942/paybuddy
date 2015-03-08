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
	 var input = $("ydp_input_text").val();
	// assuming email only.
	if(input === undefined || input == "") {
		$("#display_msg").html("enter valid email");
		$("#display_msg").show();	     
	}else {

		$.ajax({
			url: 'payment/msg?messageSendType=email&payerMobile=&payerEmail='+input+'&txnid=newtxnid&amount=1234&productInfo=product1&firstName=name&email=dudani.priyanka',
            dataType: 'json',
            type: 'post',
            contentType: 'application/json; charset=utf-8',
            success: function(data){
                	console.log("success ajax");
            },
            error: function(){
                    console.log("ajax error");
                }
            });

	}
      });
});
