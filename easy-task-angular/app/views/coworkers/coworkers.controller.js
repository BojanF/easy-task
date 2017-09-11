/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('CoworkersController', CoworkersController);

  CoworkersController.$inject = ['$log', 'CoworkersService','$cookies','$location'];

  /* @ngInject */
  function CoworkersController($log, CoworkersService,$cookies,$location) {
    var vm = this;

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
        loadGif: true,
        showTable: false,
        showEligibleUsers: false,
        showNoEligibleUsers: false,
        showErrorPanel: false,
        loaded: false
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

    vm.fetchedData = {
      coworkers: [],
      receivedRequests: [],
      sentRequests: [],
      user: {}
    };

    if($cookies.get('id')) {
      vm.USER_ID=$cookies.get('id');
      getCoworkers();
    }
    else {
      $location.path('/login');
    }

    vm.getEligibleUsers = getEligibleUsersFn;
    vm.getSentRequests = getSentRequestsFn;
    vm.getReceivedRequests = getReceivedRequestsFn;

    vm.acceptRequest = acceptRequestFn;
    vm.refuseRequest = refuseRequestFn;
    vm.removeAsCoworker = removeAsCoworkerFn;
    vm.cancelRequest = cancelRequestFn;

    vm.refreshCoworkers = refreshCoworkersFn;
    vm.refreshSentRequests = refreshSentRequests;
    vm.refreshReceivedRequests = refreshReceivedRequestsFn;



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
        button.prop('disabled',true);
      }
    }

    function getEligibleUsersFn(){
      if(!vm.uiState.eligibleUsers.loaded){
        vm.uiState.eligibleUsers.loaded = true;

        CoworkersService.getEligibleUsers(vm.USER_ID).then(
          function(data){
            //successCallback

          },
          function(){
            //errorCallback
          }
        );



      }
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
            button.prop('disabled',true);
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
        button.prop('disabled',true);
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
          button.prop('disabled',true);
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
        function(data){
          //success

          refreshCoworkersFn();
          refreshReceivedRequestsFn();
          vm.uiState.received.successMsg = "Successfully accepted request from ";
        },
        function(){
          //error
          vm.uiState.received.errorMsg = "Try again later";
          var button = $(".acceptRequest");
          button.html('<i class="fa fa-plus"></i> Accept');
          button.prop('disabled',true);
        }
      );
    }
  }

})(angular);
