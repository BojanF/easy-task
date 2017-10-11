/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('HomePageService', HomePageServiceFn);

  HomePageServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function HomePageServiceFn($log, $resource) {
    var userResource = $resource('https://kostancev.com:8080/api/user/:id', {}, {
      // update:{isArray:false, method:'PUT'}
    });
    var homePageResource = $resource('https://kostancev.com:8080/api/home-page/tasks-states/:id', {}, {});
    var activeTasksResource = $resource('https://kostancev.com:8080/api/home-page/active-tasks/:id', {}, {});
    var urgentProjectsResource = $resource('https://kostancev.com:8080/api/home-page/urgent-projects/:id', {}, {});

    var service = {
      getUser: getUserFn,
      getTasksStates: getTasksStatesFn,
      getActiveTasks: getActiveTasksFn,
      getUrgentProjects: getUrgentProjectsFn
    };
    return service;

    function getUserFn(userId){
      return userResource.get({id:userId}).$promise;
    }

    function getTasksStatesFn(userId){
      //In order to handle arrays with the $resource service, it's suggested that you use the query method
      return homePageResource.query({id:userId}).$promise;
    }

    function getActiveTasksFn(userId){
      return activeTasksResource.query({id:userId}).$promise;
    }

    function getUrgentProjectsFn(userId){
      return urgentProjectsResource.query({id:userId}).$promise;
    }


  }

})(angular);
