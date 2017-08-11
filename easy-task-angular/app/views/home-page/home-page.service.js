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
    var userResource = $resource('http://localhost:8000/api/user/:id', {}, {
      // update:{isArray:false, method:'PUT'}
    });
    var homePageResource = $resource('http://localhost:8000/api/home-page/tasks-states/:id', {}, {});
    var activeTasksResource = $resource('http://localhost:8000/api/home-page/active-tasks/:id', {}, {});
    var urgentProjectsResource = $resource('http://localhost:8000/api/home-page/urgent-projects/:id', {}, {});

    var service = {
      getUser: getUserFn,
      getTasksStates: getTasksStatesFn,
      getActiveTasks: getActiveTasksFn,
      getUrgentProjects: getUrgentProjectsFn
      // update: updateFn,
      // getById: getByIdFn,

      // remove: removeFn
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

    // function saveFn(courseEntity) {
    //   if(courseEntity.id === undefined) {
    //     return resource.save(courseEntity, function(data){
    //       courseEntity.id=data.id;
    //     }).$promise;
    //   }
    //   return updateFn(courseEntity);
    //
    //
    // }
    //
    // function updateFn(courseEntity) {
    //   if (courseEntity.id === undefined) {
    //     $log.debug("IFFFF");
    //     return saveFn(courseEntity).$promise;
    //   }
    //   $log.debug("UPDATE");
    //   return resource.update(/*{id: courseEntity.id},*/ courseEntity).$promise;
    //
    // }
    //
    // function getByIdFn(courseId) {
    //   return resource.get({id:courseId}).$promise;
    //
    // }
    //

    //
    // function removeFn(courseEntity) {
    //   $log.debug("DELETE");
    //   return resource.delete({id:courseEntity.id}).$promise;
    // }
  }

})(angular);
