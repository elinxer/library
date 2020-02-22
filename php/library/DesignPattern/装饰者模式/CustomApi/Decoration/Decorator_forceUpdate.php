<?php

namespace Common\CustomApi\Decoration;

use Common\CustomApi\Base\BaseDecorator;
use Illuminate\Support\Arr;

/**
 * 包强更输出逻辑
 *
 * Class Decorator_forceUpdate
 * @package Common\CustomApi\Decoration
 * @author Elinx <yangdongsheng>
 * @date 2019-12-05 16:24
 */
class Decorator_forceUpdate extends BaseDecorator
{
    public function handle(array $data): array
    {
        // 传入条件
        $condition  = $this->condition; // $inputs
        $data       = current($data ?? []); // 只允许一条，可以设置多个字段条件
        $need_force = 0; // 默认不强更

        $package_url = Arr::get($data, 'package_url', '');
        $is_browser  = Arr::get($data, 'is_browser', 0);
        $device_id   = Arr::get($condition, 'device_imei', '');
        $sdk_version = Arr::get($condition, 'sdk_version', '');
        $apk_version_code = Arr::get($condition, 'apk_version_code', '');

        $devices = Arr::get($data, 'device_id', '');
        $devices = explode('|', $devices);
        $devices = array_filter($devices);

        $versions = Arr::get($data, 'sdk_version', '');
        $versions = explode('|', $versions);
        $versions = array_filter($versions);

        $version_code = Arr::get($data, 'apk_version_code', 0);

        if (isset($condition['game_id']) && $condition['game_id'] == $data['game_id']) {
            // 设备不为空，但不在配置里不进行强更
            if (!empty($devices) && in_array($device_id, $devices)) {
                $need_force = 1;
            }
            // 版本配置不为空，但不在配置里不进行强更
            if (!empty($versions) && in_array($sdk_version, $versions)) {
                $need_force = 1;
            }
            // apk版本号
            if ($version_code && $version_code > $apk_version_code) {
                $need_force = 1;
            }
            // 没有填写包地址不强更
            if (empty($package_url)) {
                $need_force = 0;
            }
        }

        return [
            'need_force'  => $need_force,
            'package_url' => $package_url,
            'is_browser'  => intval($is_browser),
        ];
    }

}
