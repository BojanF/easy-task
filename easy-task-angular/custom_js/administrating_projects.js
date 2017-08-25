/**
 * Created by Bojan on 8/26/2017.
 */

$(document).on('click',".removeProject",function(){
  $(this).html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Removing...');
  $(this).prop('disabled',true);
});
