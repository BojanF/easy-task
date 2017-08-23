/**
 * Created by Bojan on 8/23/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('CoworkersService', CoworkersServiceFn);

  CoworkersServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function CoworkersServiceFn($log, $resource) {


    var getCoworkersResource = $resource('http://localhost:8000/api/coworkers/get-coworkers/:id', {}, {});
    var coworkersResource = $resource('http://localhost:8000/api/coworkers/delete/:userA/:userB', {}, {});
    var getEligibleUsersResource = $resource('http://localhost:8000/api/coworkers/eligible/:id', {}, {});
    var getReceivedRequestsResource = $resource('http://localhost:8000/api/coworkers/received/:id', {}, {});
    var getUserResource = $resource('http://localhost:8000/api/user/:id', {}, {});
    var deleteRequestResource = $resource('http://localhost:8000/api/coworkers/delete-request/:userA/:userB', {}, {}); //za cance Req isto delete req ke se vika
    var acceptRequestResource = $resource('http://localhost:8000/api/coworkers/accept-request/:id', {}, {});
    var getSentRequestsResource = $resource('http://localhost:8000/api/coworkers/sent/:id', {}, {});

    var service = {

      getCoworkers: getCoworkersFn,
      getEligibleUsers: getEligibleUsersFn,
      getSentRequests: getSentRequestsFn,
      getReceivedRequests: getReceivedRequestsFn,
      getUser: getUserFn,
      acceptRequest: acceptRequestFn,
      deleteRequest: deleteRequestFn,
      removeAsCoworker: removeAsCoworkerFn

    };
    return service;



    function getCoworkersFn(userId){
      return getCoworkersResource.query({id:userId}).$promise;
    }

    function removeAsCoworkerFn(coworkerId){

      return coworkersResource.delete({userA:coworkerId.uA, userB:coworkerId.uB}).$promise;

    }

    function getEligibleUsersFn(userId){
      return getEligibleUsersResource.query({id:userId}).$promise;
    }

    function getReceivedRequestsFn(userId){
      return getReceivedRequestsResource.query({id:userId}).$promise;
    }

    function getUserFn(userId){
      return getUserResource.get({id:userId}).$promise;
    }

    function acceptRequestFn(coworkers){
      console.log(coworkers);
      return acceptRequestResource.save(coworkers).$promise;
    }

    function deleteRequestFn(coworkerId){
      return deleteRequestResource.delete({userA:coworkerId.uA, userB:coworkerId.uB}).$promise;
    }

    function getSentRequestsFn(userId){
      return getSentRequestsResource.query({id:userId}).$promise;
    }




  }

})(angular);
