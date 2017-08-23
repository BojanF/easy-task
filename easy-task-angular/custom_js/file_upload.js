/**
 * Created by Marijo on 23-Aug-17.
 */

$('#file').filestyle({
  buttonClass : 'btn-danger',
  text : '<i class="fa fa-upload" aria-hidden="true"> </i> Choose file to upload'
});

$("#file").change(function(){
  if($("#file").val()!=null){
    $("#documentFormSubmit").prop('disabled', false);
  }
});

$('#documentFormSubmit').click(function(){
    $('#fileUploading').show();
    $('#documentFormCancel').prop('disabled',true);
});

$('#documentFormCancel').click(function(){
  $('#file_name').val(" ");
  $('#file').val(null);
  $("#documentFormSubmit").prop('disabled', true);
});
