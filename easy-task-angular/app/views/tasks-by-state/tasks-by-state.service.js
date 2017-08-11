/**
 * Created by Bojan on 8/10/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('TasksByStateService', TasksByStateServiceFn);

  TasksByStateServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function TasksByStateServiceFn($log, $resource) {

    var tasksByStateResource = $resource('http://localhost:8000/api/home-page/tasks-by-state/:id/:state', {}, {});

    var service = {
      getTasks: getTasksFn
    };
    return service;

    function getTasksFn(user_id, state){
      return tasksByStateResource.query({id:user_id, state:state}).$promise;
    }




  }

})(angular);
