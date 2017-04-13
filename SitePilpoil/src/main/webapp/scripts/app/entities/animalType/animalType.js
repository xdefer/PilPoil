'use strict';

angular.module('pilpoilApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('animalType', {
                parent: 'entity',
                url: '/animalTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'pilpoilApp.animalType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/animalType/animalTypes.html',
                        controller: 'AnimalTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('animalType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('animalType.detail', {
                parent: 'entity',
                url: '/animalType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'pilpoilApp.animalType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/animalType/animalType-detail.html',
                        controller: 'AnimalTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('animalType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AnimalType', function($stateParams, AnimalType) {
                        return AnimalType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('animalType.new', {
                parent: 'animalType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/animalType/animalType-dialog.html',
                        controller: 'AnimalTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    label: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('animalType', null, { reload: true });
                    }, function() {
                        $state.go('animalType');
                    })
                }]
            })
            .state('animalType.edit', {
                parent: 'animalType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/animalType/animalType-dialog.html',
                        controller: 'AnimalTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AnimalType', function(AnimalType) {
                                return AnimalType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('animalType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('animalType.delete', {
                parent: 'animalType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/animalType/animalType-delete-dialog.html',
                        controller: 'AnimalTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AnimalType', function(AnimalType) {
                                return AnimalType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('animalType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
