'use strict';

angular.module('pilPoilApp')
    .controller('AnimalTypeDetailController', function ($scope, $rootScope, $stateParams, entity, AnimalType, Animal, Breed) {
        $scope.animalType = entity;
        $scope.load = function (id) {
            AnimalType.get({id: id}, function(result) {
                $scope.animalType = result;
            });
        };
        var unsubscribe = $rootScope.$on('pilPoilApp:animalTypeUpdate', function(event, result) {
            $scope.animalType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
