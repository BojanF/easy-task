/**
 * Created by Bojan on 10/17/2016.
 */
(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('StudentService', StudentServiceFn);

  StudentServiceFn.$inject = ['$log', '$timeout', '$q'];

  /* @ngInject */
  function StudentServiceFn($log, $timeout, $q) {
    var studentList = [];
    var studentIdSequence = 0;

    var service = {
      save: saveFn,
      update: updateFn,
      getById: getByIdFn,
      getAll: getAllFn,
      remove: removeFn
    };


    return service;


    function saveFn(studentEntity) {
      var entityForSave, invalidMessage;
      var deferred = $q.defer();

      if (studentEntity.id === undefined) {
        $timeout(function () {
          entityForSave = angular.copy(studentEntity);
          invalidMessage = validateStudent(entityForSave);
          if (invalidMessage === null) {
            entityForSave.id = ++studentIdSequence;
            studentList.push(entityForSave);
            $log.debug('saving', entityForSave);
            deferred.resolve(entityForSave);
          } else {
            $log.debug('saving invalid:', invalidMessage);
            deferred.reject({
              message: invalidMessage
            });
          }
        }, 100);
        return deferred.promise;
      } else {
        return updateFn(studentEntity);
      }
      $log.debug('in save fn');

    }

    function validateStudent(entity) {
      if (entity.name === null
        || entity.name === undefined
        || typeof entity.name !== 'string'
        || entity.name.length === 0) {
        return 'Invalid name for Student';
      }
      return null;
    }

    function updateFn(studentEntity) {
      var deferred = $q.defer();
      if (studentEntity.id === undefined) {
        return saveFn(studentEntity);
      } else {
        $timeout(function () {
          getByIdFn(studentEntity.id)
            .then(function (savedEntity) {
              angular.extend(savedEntity, studentEntity);
              $log.debug("merged entity", savedEntity);
              $log.debug('updating', savedEntity);
              deferred.resolve(savedEntity);
            });

        }, 100);
        return deferred.promise;
      }
    }

    function getByIdFn(studentId) {
      var index;
      var deferred = $q.defer();
      $timeout(function () {
        $log.debug('get by id: ', studentId);
        index = findIndexById(studentId);
        if (index === -1) {
          deferred.resolve(null);
        } else {
          deferred.resolve(studentList[index]);
        }
      }, 100);
      return deferred.promise;
    }

    function getAllFn() {
      var deferred = $q.defer();
      $timeout(function () {
        $log.debug('getAll');
        deferred.resolve(angular.copy(studentList));
      }, 100);
      return deferred.promise;
    }

    function removeFn(studentEntity) {
      var deferred = $q.defer();
      $timeout(function () {
        var index = findIndexById(studentEntity.id);
        if (index !== -1) {
          studentList.splice(index, 1);
        }
        $log.debug('remove', studentEntity);
        deferred.resolve();
      }, 100);
      return deferred.promise;
    }

    function findIndexById(studentId) {
      var result = -1, item;

      $log.debug('get index by id: ', studentId);
      for (var i = 0; i < studentList.length; i++) {
        item = studentList[i];
        if (item.id === studentId) {
          result = i;
          break;
        }
      }
      return result;
    }

  }

})(angular);

