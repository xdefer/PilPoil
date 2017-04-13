'use strict';

angular.module('pilpoilApp')
	.controller('AdDeleteController', function($scope, $uibModalInstance, entity, Ad) {

        $scope.ad = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Ad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
