'use strict';

angular.module('pilpoilwebApp')
    .controller('BreedDetailController', function ($scope, $rootScope, $stateParams, entity, Breed, Animal, AnimalType) {
        $scope.breed = entity;
        $scope.load = function (id) {
            Breed.get({id: id}, function(result) {
                $scope.breed = result;
            });
        };
        var unsubscribe = $rootScope.$on('pilpoilwebApp:breedUpdate', function(event, result) {
            $scope.breed = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
