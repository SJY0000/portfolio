$(function(){
	getJson();
});

function getJson(){
	$.getJSON("TranditionMarket.json", function(data){
		$.each(data, function(key, val){
			if(key == "records"){
				var list = val;
				for(var i = 0; i < list.length; i++){
					var str = list[i];
					if(str.소재지도로명주소 == ""){
						$("#data").append(
						"<input type='hidden' name='market' value='"+
						str.시장명+"/"+str.시장유형+"/"+str.소재지지번주소+"/"+str.시장개설주기+"/"+str.위도+"/"+str.경도+"/"+str.점포수+"/"+str.취급품목+"/"+str.공중화장실보유여부+"/"+str.주차장보유여부+"'>"
						)
					}
					else{
						$("#data").append(
						"<input type='hidden' name='market' value='"+
						str.시장명+"/"+str.시장유형+"/"+str.소재지도로명주소+"/"+str.시장개설주기+"/"+str.위도+"/"+str.경도+"/"+str.점포수+"/"+str.취급품목+"/"+str.공중화장실보유여부+"/"+str.주차장보유여부+"'>"
						)
					}
				}
			}
		});
	});
}