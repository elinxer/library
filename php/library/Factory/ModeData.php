<?php

namespace Common;


/**
 * @method RedBag\Activity Active(...$args)
 *
 * Class ModeData
 * @package Y9377\Data
 */
class ModeData extends DataFactory
{

    public static function modeClass()
    {
        $classArr = parent::modeClass();

        $classMap = [
            'Active' => RedBag\Activity::class,
        ];

        return array_merge($classArr, $classMap);
    }

}
