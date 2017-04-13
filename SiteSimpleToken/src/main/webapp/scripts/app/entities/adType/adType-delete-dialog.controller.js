'use strict';

angular.module('pilpoilwebApp')
	.controller('AdTypeDeleteController', function($scope, $uibModalInstance, entity, AdType) {

        $scope.adType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AdType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
