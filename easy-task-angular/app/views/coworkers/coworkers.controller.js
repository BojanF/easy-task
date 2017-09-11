/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('CoworkersController', CoworkersController);

  CoworkersController.$inject = ['$log', 'CoworkersService', '$cookies', '$location'];

  /* @ngInject */
  function CoworkersController($log, CoworkersService, $cookies, $location) {
    var vm = this;
    vm.USER_ID = 1;
    vm.uiState = {
      coworkers: {
        loadGif: true,
        showCoworkers: false,
        showNoCoworkersPanel: false,
        showErrorPanel: false,
        successMsg: null,
        errorMsg: null
      },
      eligibleUsers: {
        showEligibleUsers: false,
        showNoEligibleUsers: false,
        successPanel: null,
        errorPanel: null
      },
      sent: {
        loadGif: true,
        showSentRequests: false,
        showNoSentRequests: false,
        showErrorPanel: false,
        loaded: false,
        successMsg: null,
        errorMsg: null
      },
      received: {
        loadGif: true,
        showReceivedRequests: false,
        showNoReceivedRequests: false,
        showErrorPanel: false,
        loaded: false,
        successMsg: null,
        errorMsg: null
      }
    };
    vm.searchCriteria = null;
    vm.fetchedData = {
      coworkers: [],
      searchResults: [],
      receivedRequests: [],
      sentRequests: [],
      user: {}
    };



    vm.searchEligibleUsers = searchEligibleUsersFn;

    vm.getSentRequests = getSentRequestsFn;
    vm.getReceivedRequests = getReceivedRequestsFn;

    vm.acceptRequest = acceptRequestFn;
    vm.refuseRequest = refuseRequestFn;
    vm.removeAsCoworker = removeAsCoworkerFn;
    vm.cancelRequest = cancelRequestFn;
    vm.sendRequest = sendRequestFn;

    vm.refreshCoworkers = refreshCoworkersFn;
    vm.refreshSentRequests = refreshSentRequests;
    vm.refreshReceivedRequests = refreshReceivedRequestsFn;


    if($cookies.get('id')) {
      vm.USER_ID=$cookies.get('id');
      getCoworkers();
    }
    else {
      $location.path('/login');
    }

    function getCoworkers(){
      CoworkersService.getCoworkers(vm.USER_ID).then(successCallbackCoworkers, errorCallbackCoworkers);

      function successCallbackCoworkers(data){
        vm.fetchedData.coworkers = data;
        vm.uiState.coworkers.loadGif = false;
        vm.uiState.coworkers.showErrorPanel = false;

        if(vm.fetchedData.coworkers.length > 0){
          vm.uiState.coworkers.showCoworkers = true;
          vm.uiState.coworkers.showNoCoworkersPanel = false;
        }
        else{
          vm.uiState.coworkers.showCoworkers = false;
          vm.uiState.coworkers.showNoCoworkersPanel = true;
        }
      }

      function errorCallbackCoworkers(){
        vm.uiState.coworkers.loadGif = false;
        vm.uiState.coworkers.showCoworkers = false;
        vm.uiState.coworkers.showNoCoworkersPanel = false;
        vm.uiState.coworkers.showErrorPanel = true;
      }
    }

    function refreshCoworkersFn() {
      vm.uiState.coworkers = {loadGif:true,
        showNoCoworkers:false,
        showNoCoworkersPanel: false,
        showErrorPanel: false,
        successMsg: null,
        errorMsg: null
      };
      getCoworkers();

    }

    function removeAsCoworkerFn(coworkerUserId){
      console.log(coworkerUserId);
      vm.uiState.coworkers.successMsg = null;
      vm.uiState.coworkers.errorMsg = null;
      var coworkerId = {uA:vm.USER_ID, uB:coworkerUserId};
      CoworkersService.removeAsCoworker(coworkerId).then(successRemove, errorRemove);

      function successRemove(){
        refreshCoworkersFn();
        vm.uiState.coworkers.successMsg = "Successfully removed coworker";
      }

      function errorRemove(){
        vm.uiState.coworkers.errorMsg = "Refresh the page and try again later!";
        var button = $(".removeCoworker");
        button.html('<i class="fa fa-times"></i> Remove as coworker');
        button.prop('disabled',false);
      }
    }

    function searchEligibleUsersFn(){
      vm.uiState.eligibleUsers = {showEligibleUsers:false,
        showNoEligibleUsers:false,
        successPanel: null,
        errorPanel:null};
      var searchButton = $(".search");
      searchButton.html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Searching...');
      searchButton.prop('disabled',true);
      var firstWord = vm.searchCriteria.split(" ")[0];
      CoworkersService.searchEligibleUsers(vm.USER_ID, firstWord).then(
        function(data){
          //successCallback

          vm.fetchedData.searchResults = data;
          console.log(vm.fetchedData.searchResults);
          searchButton.html('<i class="fa fa-fw fa-search"></i> Search');
          searchButton.prop('disabled',false);

          if(vm.fetchedData.searchResults.length > 0){
            vm.uiState.eligibleUsers.showEligibleUsers = true;
            vm.uiState.eligibleUsers.showNoEligibleUsers = false;
          }
          else{
            vm.uiState.eligibleUsers.showEligibleUsers = false;
            vm.uiState.eligibleUsers.showNoEligibleUsers = true;
          }
        },
        function(){
          //errorCallback

          searchButton.html('<i class="fa fa-fw fa-search"></i> Search');
          searchButton.prop('disabled',false);
          vm.uiState.eligibleUsers = {showEligibleUsers:false,
            showNoEligibleUsers:false,
            successPanel: null,
            errorPanel:'We run into an error! Try again later!'};

        }
      );
    }

    function getReceivedRequestsFn(){

      if(!vm.uiState.received.loaded){
        console.log("received");
        vm.uiState.received.loaded = true;

        CoworkersService.getReceivedRequests(vm.USER_ID).then(
          function(data){
            //successCallback
            vm.fetchedData.receivedRequests = data;

            vm.uiState.received.loadGif = false;
            vm.uiState.received.showErrorPanel = false;

            if(vm.fetchedData.receivedRequests.length > 0){
              vm.uiState.received.showReceivedRequests = true;
              vm.uiState.received.showNoReceivedRequests = false;
            }
            else{
              vm.uiState.received.showReceivedRequests = false;
              vm.uiState.received.showNoReceivedRequests = true;
            }
          },
          function(){
            //errorCallback
            vm.uiState.received.loadGif = false;
            vm.uiState.received.showReceivedRequests = false;
            vm.uiState.received.showNoReceivedRequests = false;
            vm.uiState.received.showErrorPanel = true;
          }
        );

      }
    }

    function sendRequestFn(userB){
      var coworkers = {};
      vm.uiState.eligibleUsers.successPanel = null;
      vm.uiState.eligibleUsers.errorPanel = null;
      if(vm.fetchedData.user.id == undefined) {
        console.log("ZEMAM USER");
        CoworkersService.getUser(vm.USER_ID).then(

          function(data) {
            //success fetching user
            vm.fetchedData.user = data;
            coworkers = {
              id: {
                userA: vm.fetchedData.user.id,
                userB: userB.id
              },
              userA: vm.fetchedData.user,
              userB: userB,
              state: 'PENDING'
            };
            saveCoworkersRequest(coworkers);

          },
          function () {
            //error fetching user
            vm.uiState.eligibleUsers.errorPanel = "Try again later";
            var button = $(".sendRequest");
            button.html('<i class="fa fa-plus-square"></i> Send request');
            button.prop('disabled',false);
          }
        );
      }
      else{
        console.log("IMAM USER");
        coworkers = {
          id: {
            userA: vm.fetchedData.user.id,
            userB: userB.id
          },
          userA: vm.fetchedData.user,
          userB: userB,
          state: 'PENDING'
        };

        saveCoworkersRequest(coworkers);
      }
    }

    function refreshReceivedRequestsFn(){

      vm.uiState.received= {loadGif:true,
        showReceivedRequests:false,
        showNoReceivedRequests:false,
        showErrorPanel:false,
        loaded:false,
        successMsg:null,
        errorMsg:null
      };
      getReceivedRequestsFn();
    }

    function acceptRequestFn(userB){
      var coworkers = {};
      vm.uiState.received.successMsg = null;
      vm.uiState.received.errorMsg = null;
      if(vm.fetchedData.user.id == undefined) {
        console.log("ZEMAM USER");
        CoworkersService.getUser(vm.USER_ID).then(

          function(data) {
            //success fetching user
            vm.fetchedData.user = data;
            coworkers = {
              id: {
                userA: vm.fetchedData.user.id,
                userB: userB.id
              },
              userA: vm.fetchedData.user,
              userB: userB,
              state: 'ACCEPTED'
            };
            saveCoworkers(coworkers);

          },
          function () {
            //error fetching user
            vm.uiState.received.errorMsg = "Try again later";
            var button = $(".acceptRequest");
            button.html('<i class="fa fa-plus"></i> Accept');
            button.prop('disabled',false);
          }
        );
      }
      else{
        console.log("IMAM USER");
        coworkers = {
          id: {
            userA: vm.fetchedData.user.id,
            userB: userB.id
          },
          userA: vm.fetchedData.user,
          userB: userB,
          state: 'ACCEPTED'
        };

        saveCoworkers(coworkers);
      }
    }

    function refuseRequestFn(userAid){
      vm.uiState.received.successMsg = null;
      vm.uiState.received.errorMsg = null;
      var coworkerId = {uA:userAid, uB:vm.USER_ID};
      CoworkersService.deleteRequest(coworkerId).then(successRefuse, errorRefuse);

      function successRefuse(data){
        refreshReceivedRequestsFn();
        console.log("STDO "+data);
        vm.uiState.received.successMsg = "Successfully turned down offer";
      }

      function errorRefuse(){
        vm.uiState.received.errorMsg = "Try again later!";
        var button = $(".refuseRequest");
        button.html('<i class="fa fa-times"></i> Turn down');
        button.prop('disabled',false);
      }
    }

    function getSentRequestsFn(){
      console.log("DA");
      if(!vm.uiState.sent.loaded){
        console.log("get sent req");
        vm.uiState.sent.loaded = true;
        CoworkersService.getSentRequests(vm.USER_ID).then(
          function (data) {
            //success callback
            vm.fetchedData.sentRequests = data;

            vm.uiState.sent.loadGif = false;
            vm.uiState.sent.showErrorPanel = false;
            if(vm.fetchedData.sentRequests.length > 0){
              vm.uiState.sent.showSentRequests = true;
              vm.uiState.sent.showNoSentRequests = false;
            }
            else{
              vm.uiState.sent.showSentRequests = false;
              vm.uiState.sent.showNoSentRequests = true;
            }
          },
          function (){
            //error callback
            vm.uiState.sent.loadGif = false;
            vm.uiState.sent.showSentRequests = false;
            vm.uiState.sent.showNoSentRequests = false;
            vm.uiState.sent.showErrorPanel = true;
          }
        );

      }
    }

    function cancelRequestFn(userBid){
      vm.uiState.sent.successMsg = null;
      vm.uiState.sent.errorMsg = null;
      var coworkerId = {uA:vm.USER_ID, uB:userBid };
      CoworkersService.deleteRequest(coworkerId).then(
        function(){
          //successCallback
          refreshSentRequests();
          vm.uiState.sent.successMsg = "Successfully canceled request";
        },
        function(){
          //errorCallback
          vm.uiState.sent.errorMsg = "Try again later!";
          var button = $(".cancelRequest");
          button.html('<i class="fa fa-ban"></i> Cancel request');
          button.prop('disabled',false);
        }
      );
    }

    //helper functions

    function refreshSentRequests(){
      vm.uiState.sent = {
        loadGif: true,
        showSentRequests: false,
        showNoSentRequests: false,
        showErrorPanel: false,
        loaded: false,
        successMsg: null,
        errorMsg: null
      };
      getSentRequestsFn();
      refreshCoworkersFn();
    }

    function saveCoworkers(coworkers){
      console.log(coworkers);
      CoworkersService.acceptRequest(coworkers).then(
        function(){
          //success

          refreshCoworkersFn();
          refreshReceivedRequestsFn();
          vm.uiState.received.successMsg = "Successfully accepted request!";
        },
        function(){
          //error
          vm.uiState.received.errorMsg = "Try again later";
          var button = $(".acceptRequest");
          button.html('<i class="fa fa-plus"></i> Accept');
          button.prop('disabled',false);
        }
      );
    }

    function saveCoworkersRequest(coworkers){
      console.log(coworkers);
      CoworkersService.sendRequest(coworkers).then(
        function(){
          //success
          refreshSentRequests();
          searchEligibleUsersFn();
          vm.uiState.eligibleUsers.successPanel = "Request is successfully sent! ";
        },
        function(){
          //error
          vm.uiState.eligibleUsers.errorPanel = "Try again later";
          var button = $(".sendRequest");
          button.html('<i class="fa fa-plus-square"></i> Send request');
          button.prop('disabled',false);
        }
      );
    }
  }

})(angular);
