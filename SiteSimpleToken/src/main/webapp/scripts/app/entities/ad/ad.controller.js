'use strict';

angular.module('pilpoilwebApp')
    .controller('AdController', function ($scope, $state, Ad, AdSearch) {

        $scope.ads = [];
        $scope.loadAll = function() {
            Ad.query(function(result) {
               $scope.ads = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AdSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.ads = result;
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
            $scope.ad = {
                description: null,
                recover: null,
                date: null,
                phone: null,
                email: null,
                adress: null,
                complement: null,
                postalCode: null,
                city: null,
                country: null,
                longitude: null,
                lattitude: null,
                id: null
            };
        };
    });
