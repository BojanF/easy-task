/**
 * Created by Bojan on 8/24/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('TaskDetailsService', TaskDetailsServiceFn);

  TaskDetailsServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function TaskDetailsServiceFn($log, $resource) {


    var taskResource = $resource('http://localhost:8000/api/task/:id', {}, {});
    var taskUpdateResource = $resource('http://localhost:8000/api/task/update/:id', {}, {});

    var service = {

      getTask: getTaskFn,
      updateTask: updateTaskFn


    };
    return service;



    function getTaskFn(taskId){
      return taskResource.get({id:taskId}).$promise;
    }

    function updateTaskFn(task){
      return taskUpdateResource.save(task).$promise;
    }




  }

})(angular);
