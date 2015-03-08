<%@ page import="java.util.*" %>
<%@ page import="java.security.*" %>

<%!
public boolean empty(String s)
	{
		if(s== null || s.trim().equals(""))
			return true;
		else
			return false;
	}
%>


<%!
	public String hashCal(String type,String str){
		byte[] hashseq=str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try{
		MessageDigest algorithm = MessageDigest.getInstance(type);
		algorithm.reset();
		algorithm.update(hashseq);
		byte messageDigest[] = algorithm.digest();
            
		

		for (int i=0;i<messageDigest.length;i++) {
			String hex=Integer.toHexString(0xFF & messageDigest[i]);
			if(hex.length()==1) hexString.append("0");
			hexString.append(hex);
		}
			
		}catch(NoSuchAlgorithmException nsae){ }
		
		return hexString.toString();


	}
%>


<% 	
	String merchant_key="C0Dr8m";
	String salt="3sf0jURk";
	String action1 ="";
	String base_url="https://test.payu.in";
	int error=0;
	String hashString="";
	
	Enumeration paramNames = request.getParameterNames();
	
	//Map<String,String> params= new HashMap<String,String>();
	Map<String,String> params = (Map)request.getAttribute("data");
/*     	while(paramNames.hasMoreElements()) 
	{
      		String paramName = (String)paramNames.nextElement();
      
      		String paramValue = request.getParameter(paramName);

		params.put(paramName,paramValue);
	} */
	String txnid ="";
	if(empty(params.get("txnid"))){
		//error 
	}
	else {
		txnid=params.get("txnid");
	}

	String txn="abcd";
	String hash="";
	String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
	if(empty(params.get("hash")) && params.size()>0){
		if( empty(params.get("key"))
			|| empty(params.get("txnid"))
			|| empty(params.get("amount"))
			|| empty(params.get("firstname"))
			|| empty(params.get("email"))
			|| empty(params.get("phone"))
			|| empty(params.get("productinfo"))
			|| empty(params.get("surl"))
			|| empty(params.get("furl"))	)
			
			error=1;
		else{
			error=1;
		}
	}
	else if(!empty(params.get("hash")))
	{
		hash=params.get("hash");
		action1=base_url.concat("/_payment");
	}
%>
<html>

<script>
var hash='<%= hash %>';
function submitPayuForm() {
	if (hash == '')
		return;
      var payuForm = document.forms.payuForm;
      payuForm.submit();
    }
</script>

<body onload="submitPayuForm();">

<form action="<%= action1 %>" method="post" name="payuForm">
<input type="hidden" name="key" value="<%= merchant_key %>" />
      <input type="hidden" name="hash" value="<%= hash %>"/>
      <input type="hidden" name="txnid" value="<%= txnid %>" />
      <div> Redirecting to payment site </div>
      
      <table>
        <tr>
          <td style="display:none;"><b>Mandatory Parameters</b></td>
        </tr>
        <tr>
          <td style="display:none;">Amount: </td>
          <td><input type="hidden" name="amount" value="<%= (empty(params.get("amount"))) ? "" : params.get("amount") %>" /></td>
          <td style="display:none;">First Name: </td>
          <td><input type="hidden" name="firstname" id="firstname" value="<%= (empty(params.get("firstname"))) ? "" : params.get("firstname") %>" /></td>
        </tr>
        <tr>
          <td style="display:none;">Email: </td>
          <td><input  type="hidden"  name="email" id="email" value="<%= (empty(params.get("email"))) ? "" : params.get("email") %>" /></td>
          <td style="display:none;">Phone: </td>
          <td><input type="hidden"  name="phone" value="<%= (empty(params.get("phone"))) ? "" : params.get("phone") %>" /></td>
        </tr>
        <tr>
          <td style="display:none;">Product Info: </td>
          <td colspan="3"><input type="hidden"  name="productinfo" value="<%= (empty(params.get("productinfo"))) ? "" : params.get("productinfo") %>" size="64" /></td>
        </tr>
        <tr>
          <td style="display:none;">Success URI: </td>
          <td colspan="3"><input type="hidden"  name="surl" value="<%= (empty(params.get("surl"))) ? "" : params.get("surl") %>" size="64" /></td>
        </tr>
        <tr>
          <td style="display:none;">Failure URI: </td>
          <td colspan="3"><input type="hidden"  name="furl" value="<%= (empty(params.get("furl"))) ? "" : params.get("furl") %>" size="64" /></td>
        </tr>
        
        <tr>
          <% if(empty(hash)){ %>
            <td colspan="4"><input type="submit" value="Submit" /></td>
          <% } %>
        </tr>
      </table>
    </form>


</body>
</html>