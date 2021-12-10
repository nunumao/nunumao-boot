<template>
  <div class="d-flex justify-content-center align-items-center h-100">
    <div class="scene"></div>
  </div>
</template>
<script>
import * as THREE from 'three'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls'
import { Capsule } from 'three/examples/jsm/math/Capsule'
import { Octree } from 'three/examples/jsm/math/Octree'
import Stats from 'three/examples/jsm/libs/stats.module'
export default {
  data: {
    container: null
  },
  mounted() {
    console.log(THREE);
    const GRAVITY = 20;

    const NUM_SPHERES = 100;
    const SPHERE_RADIUS = 0.2;

    const STEPS_PER_FRAME = 5;

    this.container = this.$el.querySelector('.scene');
    // 创建场景
    const scene = new THREE.Scene();
    // 设置背景色
    scene.background = new THREE.Color(0x88ccff);
    // 创建相机
    const camera = new THREE.PerspectiveCamera(75, this.$el.querySelector('.scene').clientWidth / this.$el.querySelector('.scene').clientHeight, 0.1, 1000);
    camera.rotation.order = 'YXZ';
    // 创建渲染器
    const renderer = new THREE.WebGLRenderer({
      alpha: true,
      antialias: true
    });
    // 设置像素比例为设备渲染比例
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setSize(this.$el.querySelector('.scene').clientWidth, this.$el.querySelector('.scene').clientHeight);
    // 设置阴影
    renderer.shadowMap.enabled = true;
    renderer.shadowMap.type = THREE.VSMShadowMap;
    // 添加到页面
    this.$el.querySelector('.scene').appendChild(renderer.domElement);
    // 辅助线
    const axisHelper = new THREE.AxesHelper(250);
    scene.add(axisHelper);

    // 添加统计
    const stats = new Stats();
    stats.domElement.style.position = 'absolute';
    stats.domElement.style.top = '0px';
    this.container.appendChild(stats.domElement);

    // 玩家(网格碰撞器)
    const playerCollider = new Capsule(new THREE.Vector3(0, 0.35, 0), new THREE.Vector3(0, 1, 0), 0.35);
    // 前进方向速度
    const playerVelocity = new THREE.Vector3();
    // 前进方向
    const playerDirection = new THREE.Vector3();

    let playerOnFloor = false;

    // 控制
    let mouseTime = 0;

    const keyStates = {};

    const vector1 = new THREE.Vector3();
    const vector2 = new THREE.Vector3();
    const vector3 = new THREE.Vector3();

    document.addEventListener('keydown', (event) => {

      keyStates[event.code] = true;

    });

    document.addEventListener('keyup', (event) => {

      keyStates[event.code] = false;

    });

    document.addEventListener('mousedown', () => {

      document.body.requestPointerLock();

      mouseTime = performance.now();

    });

    document.addEventListener('mouseup', () => {

      // throwBall();

    });

    document.body.addEventListener('mousemove', (event) => {

      if (document.pointerLockElement === document.body) {

        camera.rotation.y -= event.movementX / 500;
        camera.rotation.x -= event.movementY / 500;

      }

    });

    // 环境光
    const ambientlight = new THREE.AmbientLight(0x6688cc);
    scene.add(ambientlight);

    // 补光 1
    // const fillLight1 = new THREE.DirectionalLight(0xff9999, 0.5);
    // fillLight1.position.set(- 1, 10, 2);
    // scene.add(fillLight1);

    // 补光 2
    const fillLight2 = new THREE.DirectionalLight(0x8888ff, 0.2);
    fillLight2.position.set(0, 0, 0);
    scene.add(fillLight2);
    scene.add(new THREE.DirectionalLightHelper(fillLight2));

    // 平行光
    const directionalLight = new THREE.DirectionalLight(0xffffaa, 1.2);
    directionalLight.position.set(100, 100, 100);
    directionalLight.castShadow = true;
    directionalLight.shadow.camera.near = 0.01;
    directionalLight.shadow.camera.far = 500;
    directionalLight.shadow.camera.right = 30;
    directionalLight.shadow.camera.left = - 30;
    directionalLight.shadow.camera.top = 30;
    directionalLight.shadow.camera.bottom = - 30;
    directionalLight.shadow.mapSize.width = 1024;
    directionalLight.shadow.mapSize.height = 1024;
    directionalLight.shadow.radius = 4;
    directionalLight.shadow.bias = - 0.00006;
    scene.add(directionalLight);

    scene.add(new THREE.DirectionalLightHelper(directionalLight));


    // 加载模型
    let mixer;
    let walkAction;
    new GLTFLoader().load('/gltf/Soldier.glb', gltf => {
      let model = gltf.scene;
      model.position.set(0, 0, 0)
      scene.add(model);
      model.traverse(function (object) {
        if (object.isMesh) object.castShadow = true;
      })

      // 骨骼动画
      let skeleton = new THREE.SkeletonHelper(model);
      skeleton.visible = true;
      scene.add(skeleton);

      // 获取所有动画
      const animations = gltf.animations;
      mixer = new THREE.AnimationMixer(model);
      walkAction = mixer.clipAction(animations[3]);
      walkAction.enabled = true;
      walkAction.setEffectiveTimeScale(1);
      walkAction.setEffectiveWeight(1.0);
      walkAction.play()
    })

    // 加载地图
    const worldOctree = new Octree();
    new GLTFLoader().load('/mod/scene.gltf', (gltf) => {
      gltf.scene.position.set(0, -10, 0)
      scene.add(gltf.scene);

      worldOctree.fromGraphNode(gltf.scene);

      gltf.scene.traverse(child => {

        if (child.isMesh) {

          child.castShadow = true;
          child.receiveShadow = true;

          if (child.material.map) {

            child.material.map.anisotropy = 8;

          }

        }

      });

      animate();

    });

    function playerCollisions() {

      const result = worldOctree.capsuleIntersect(playerCollider);

      playerOnFloor = false;

      if (result) {

        playerOnFloor = result.normal.y > 0;

        if (!playerOnFloor) {

          playerVelocity.addScaledVector(result.normal, - result.normal.dot(playerVelocity));

        }

        playerCollider.translate(result.normal.multiplyScalar(result.depth));

      }

    }

    function updatePlayer(deltaTime) {

      let damping = Math.exp(- 4 * deltaTime) - 1;

      if (!playerOnFloor) {

        playerVelocity.y -= GRAVITY * deltaTime;

        // small air resistance
        damping *= 0.7;

      }

      playerVelocity.addScaledVector(playerVelocity, damping);

      const deltaPosition = playerVelocity.clone().multiplyScalar(deltaTime);
      playerCollider.translate(deltaPosition);

      playerCollisions();

      camera.position.copy(playerCollider.end);

    }

    // 获取前进向量
    function getForwardVector() {

      camera.getWorldDirection(playerDirection);
      playerDirection.y = 0;
      playerDirection.normalize();

      return playerDirection;

    }

    // 获取左右向量
    function getSideVector() {

      camera.getWorldDirection(playerDirection);
      playerDirection.y = 0;
      playerDirection.normalize();
      playerDirection.cross(camera.up);

      return playerDirection;

    }

    function controls(deltaTime) {

      // gives a bit of air control
      const speedDelta = deltaTime * (playerOnFloor ? 25 : 8);

      if (keyStates['KeyW']) {

        playerVelocity.add(getForwardVector().multiplyScalar(speedDelta));

      }

      if (keyStates['KeyS']) {

        playerVelocity.add(getForwardVector().multiplyScalar(- speedDelta));

      }

      if (keyStates['KeyA']) {

        playerVelocity.add(getSideVector().multiplyScalar(- speedDelta));

      }

      if (keyStates['KeyD']) {

        playerVelocity.add(getSideVector().multiplyScalar(speedDelta));

      }

      if (playerOnFloor) {

        if (keyStates['Space']) {

          playerVelocity.y = 10;

        }

      }

    }

    const clock = new THREE.Clock();

    const animate = function () {
      const deltaTime = Math.min(0.05, clock.getDelta()) / STEPS_PER_FRAME;

      for (let i = 0; i < STEPS_PER_FRAME; i++) {
        controls(deltaTime);
        updatePlayer(deltaTime);
        // updateSpheres(deltaTime);
        mixer.update(deltaTime)
      }

      renderer.render(scene, camera);
      stats.update();
      requestAnimationFrame(animate);
    }

    // new OrbitControls();

  }
}
</script>
<style lang="scss" scoped>
.scene {
  width: 100%;
  height: 100%;
}
</style>