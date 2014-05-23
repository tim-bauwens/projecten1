<?php

namespace ERSite\Repository;

class RegisterRepository extends \Knp\Repository {

	public function getTableName() {
		return 'users';
	}
}