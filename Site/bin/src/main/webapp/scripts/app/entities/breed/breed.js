'use strict';

angular.module('pilPoilApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('breed', {
                parent: 'entity',
                url: '/breeds',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'pilPoilApp.breed.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/breed/breeds.html',
                        controller: 'BreedController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('breed');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('breed.detail', {
                parent: 'entity',
                url: '/breed/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'pilPoilApp.breed.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/breed/breed-detail.html',
                        controller: 'BreedDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('breed');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Breed', function($stateParams, Breed) {
                        return Breed.get({id : $stateParams.id});
                    }]
                }
            })
            .state('breed.new', {
                parent: 'breed',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/breed/breed-dialog.html',
                        controller: 'BreedDialogController',
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
                        $state.go('breed', null, { reload: true });
                    }, function() {
                        $state.go('breed');
                    })
                }]
            })
            .state('breed.edit', {
                parent: 'breed',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/breed/breed-dialog.html',
                        controller: 'BreedDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Breed', function(Breed) {
                                return Breed.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('breed', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('breed.delete', {
                parent: 'breed',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/breed/breed-delete-dialog.html',
                        controller: 'BreedDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Breed', function(Breed) {
                                return Breed.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('breed', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
