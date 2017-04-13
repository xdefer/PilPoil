'use strict';

angular.module('pilpoilApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mypets', {
                parent: 'site',
                url: '/mypets',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/mypets/mypets.html',
                        controller: 'MypetsController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('mypets');
                        $translatePartialLoader.addPart('animal');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mypets.delete', {
		        parent: 'mypets',
		        url: '/delete/{id}',
		        data: {
		            authorities: ['ROLE_USER'],
		        },
		        onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
		            $uibModal.open({
		                templateUrl: 'scripts/app/mypets/mypets-delete-dialog.html',
		                controller: 'MypetsDeleteController',
		                size: 'md',
	                    resolve: {
	                        entity: ['Animal', function(Animal) {
	                            return Animal.get({id : $stateParams.id});
	                        }]
	                    }
		            }).result.then(function(result) {
		                $state.go('mypets', null, { reload: true });
		            }, function() {
		                $state.go('^');
		            })
		        }]
            })
    });
