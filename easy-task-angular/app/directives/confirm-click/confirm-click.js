/**
 * Created by Bojan on 8/31/2017.
 */

(function () {
  'use strict';
  angular.module('confirmClick', []);
  angular.module('confirmClick').directive('confirmClick', [
    '$timeout',
    '$document',
    function ($timeout, $document) {
      return {
        scope: {},
        link: function (scope, element, attrs) {
          //debugger;
          var actionText, promise, textWidth;
          actionText = element.html();
          textWidth = null;
          promise = null;
          scope.confirmingAction = false;
          element.css({ transition: 'max-width 1s' });
          scope.$watch('confirmingAction', function (newVal, oldVal) {
            var body, clone;
            if (newVal === oldVal && oldVal === false) {
              clone = element.clone();
              clone.css({
                left: '-9999px',
                position: 'absolute'
              });
              body = $document[0].body;
              body.appendChild(clone[0]);
              textWidth = clone[0].offsetWidth;
              textWidth = textWidth + 'px';
              body.removeChild(clone[0]);
              // console.log("IF 1 GORE");
            }
            if (scope.confirmingAction) {
              element.text(attrs.confirmMessage);
              element.css({ maxWidth: '300px' });
              // console.log("IF 2 GORE");
              return element.addClass('confirming');
            } else {
              element.html(actionText);
              element.css({ maxWidth: textWidth });
              // console.log("else 1 GORE");
              element.removeClass('confirmed');
              return element.removeClass('confirming');
            }
          });
          return element.bind('click', function () {
            if (!scope.confirmingAction) {
              scope.$apply(function () {
                // console.log("PRV KLIK");
                actionText = element.html();
                return scope.confirmingAction = true;

              });
              return promise = $timeout(function () {
                // console.log("SVETLO SE GASNE");
                return scope.confirmingAction = false;
              }, 1500);
            } else {

              $timeout.cancel(promise);
              // element.css({ opacity: '0.5' });
              //dodadeno od Bojan sledni 2 linii
              element.html('<img src="pictures//loading.gif" style="width:15px; height:15px;">&nbsp;Removing...');
              element.prop('disabled', true);
              // console.log("DA DA DA");
              element.removeClass('confirming');

              element.addClass('confirmed');

              actionText = element.html();
              scope.confirmingAction = false;



              return scope.$parent.$apply(attrs.confirmClick);
            }
          });
        }
      };
    }
  ]);
}.call(this));
