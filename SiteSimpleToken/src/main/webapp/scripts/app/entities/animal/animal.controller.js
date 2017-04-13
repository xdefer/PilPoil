'use strict';

angular.module('pilpoilwebApp')
    .controller('AnimalController', function ($scope, $state, Animal, AnimalSearch) {

        $scope.animals = [];
        $scope.loadAll = function() {
            Animal.query(function(result) {
               $scope.animals = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AnimalSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.animals = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.animal = {
                name: null,
                age: null,
                sexe: null,
                tatoo: null,
                chip: null,
                photo: null,
                colors: null,
                id: null
            };
        };
    });
