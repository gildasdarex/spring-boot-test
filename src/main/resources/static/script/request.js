<script type="text/javascript">
(function(){
	function doGet(url) {
		 // on fait un appel Ajax à la main
		 $.ajax({
			 headers : {
			 'Authorization' : 'Basic ' +authorizationCode
			 },
			 url : baseUrl + url,
			 type : 'GET' ,
			 dataType : 'text' ,
			 beforeSend : function() {
				 
			 },
			 success : function(data) {
				 // résultat texte
				 response.text(data);
			 },
			 complete : function() {
				 
			 },
			 error : function(jqXHR) {
				 // erreur système
				 response.text(JSON.stringify(jqXHR.statusCode()));
			 }
		 })
	 }
	
	
	 function doPost(url, posted) {
		 // on fait un appel Ajax à la main
		 $.ajax({
			 headers : {
			 'Authorization' : 'Basic ' +authorizationCode
			 },
			 url : baseUrl + url,
			 type : 'POST' ,
			 contentType : 'application/json; charset=UTF-8' ,
			 data : posted,
			 dataType : 'text' ,
			 beforeSend : function() {
			 
			 },
			 success : function(data) {
				 // résultat texte
				 response.text(data);
			 },
			 complete : function() {
			 
			 },
			 error : function(jqXHR) {
				 // erreur système
				 response.text(JSON.stringify(jqXHR.statusCode()));
			 }
		 })
	 }
		
})();
</script>