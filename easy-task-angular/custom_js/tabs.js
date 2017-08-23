/**
 * Created by Marijo on 22-Aug-17.
 */

function hideOtherContents(){
  $('#tasks-tab').hide();
  $('#documents-tab').hide();
  $('#comments-tab').hide();
  $('#info-tab').hide();
  $('#stats-tab').hide();
};

$("#tasks").click(function(){
  hideOtherContents();
  $("#tasks-tab").show();
});

$("#documents").click(function(){
  hideOtherContents();
  $("#documents-tab").show();
});

$("#comments").click(function(){
  hideOtherContents();
  $("#comments-tab").show();
});

$("#info").click(function(){
  hideOtherContents();
  $("#info-tab").show();
});

$("#stats").click(function(){
  hideOtherContents();
  $("#stats-tab").show();
});

//coworkers tabs

function hideCoworkersTabs(){
  $("#coworkers-tab").hide();
  $("#send-requests-tab").hide();
  $("#sent-requests-tab").hide();
  $("#received-request-tab").hide();
}

$("#coworkers").click(function(){
  hideCoworkersTabs();
  $("#coworkers-tab").show();
});

$("#send-requests").click(function(){
  hideCoworkersTabs();
  $("#send-requests-tab").show();
});

$("#sent-requests").click(function(){
  hideCoworkersTabs();
  $("#sent-requests-tab").show();
});

$("#received-request").click(function(){
  hideCoworkersTabs();
  $("#received-request-tab").show();
});
