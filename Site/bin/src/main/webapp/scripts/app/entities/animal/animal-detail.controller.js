'use strict';

angular.module('pilPoilApp')
    .controller('AnimalDetailController', function ($scope, $rootScope, $stateParams, entity, Animal, Ad, AnimalType, Breed) {
        $scope.animal = entity;
        $scope.load = function (id) {
            Animal.get({id: id}, function(result) {
                $scope.animal = result;
            });
        };
        var unsubscribe = $rootScope.$on('pilPoilApp:animalUpdate', function(event, result) {
            $scope.animal = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
