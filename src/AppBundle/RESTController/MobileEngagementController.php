<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 15/08/2016
 * Time: 17:52
 */

namespace AppBundle\RESTController;

use AppBundle\Entity\Fight;
use FOS\RestBundle\Controller\FOSRestController;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\Config\Definition\Exception\Exception;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use FOS\RestBundle\View\View;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;


class FightController extends FOSRestController
{

    /**
     * @Route("/sendFightEngagementNotification")
     *
     * @Method("POST")
     *
     * @return View
     */
    public function sendFightEngagementNotification(Request $request) {
        $view = null;
        $deviceId = null;
        $sender = null;

        // Get device id from request
        if($request->request->get("deviceId") != null) {
            $deviceId = $request->request->get("deviceId");
        }

        if($request->request->get("sender") != null) {
            $sender = $request->request->get("sender");
        }

        // Get access token to call Azure Mobile Engagement REST api
        $accessTokenString = $this->getAccesToken();
        $fullAuthentication = 'Bearer ' . $accessTokenString;

        // Call top Azure Mobile Engagement REST api to send a fight notification
        if ($accessTokenString != null && $deviceId != null) {

            $url = "https://management.azure.com/subscriptions/54ca7f47-8e63-4cac-8b58-5d3f193f8ac7/resourcegroups/pokemonworld-universalApp/providers/Microsoft.MobileEngagement/appcollections/pokemonworld/apps/pokemonworld-android/campaigns/announcements/3/push?api-version=2014-12-01";

            // Get cURL resource
            $curl = curl_init();

            // Prepare datas to include in request body
            $notificationMessage = array ('notificationTitle' => 'Demande de combat',
                                            'notificationMessage' => $sender . ' vous a envoyé une demande de combat');

            $datas = array ('grant_type' => 'client_credentials',
                    'client_id' => '4c863caa-953a-40ea-9fe9-837034149260',
                    'client_secret' => 'rO2Tkw4vg0+Y/owP+D5kHsTfbOUfspr64lhY8/qJlmo=',
                    'resource' => 'https://management.azure.com/',
                    'deviceIds' => array ($deviceId),
                    'data' => $notificationMessage
                );

            $jsonDatas = json_encode($datas);

            // Set some options - we are passing in a useragent too here
            curl_setopt_array($curl, array(
                CURLOPT_RETURNTRANSFER => 1,
                CURLOPT_URL => $url,
                CURLOPT_USERAGENT => 'Codular Sample cURL Request',
                CURLOPT_HTTPHEADER => array(
                    'Authorization: '.$fullAuthentication,
                    'Content-Type: application/json',
                    'Accept: application/json'
                ),
                CURLOPT_POST => 1,
                CURLOPT_POSTFIELDS => $jsonDatas
            ));

            // Send the request & save response to $resp
            $resp = curl_exec($curl);

            if(!curl_exec($curl)){
                die('Error: "' . curl_error($curl) . '" - Code: ' . curl_errno($curl));
            } else {
                $view = $this->view("Notification envoyé au dresseur", 200)->setFormat('json');
            }

            // Close request to clear up some resources
            curl_close($curl);
        }

        return $view;
    }


    /**
     * Get acces token to send engagement notification
     */
    private function getAccesToken() {
        $accessTokenString = null;
        $response = null;

        // Get cURL resource
        $curl = curl_init();

        // Set some options - we are passing in a useragent too here
        curl_setopt_array($curl, array(
            CURLOPT_RETURNTRANSFER => 1,
            CURLOPT_URL => 'https://login.microsoftonline.com/2d7a3389-0855-4331-91c5-07cd479c8115/oauth2/token',
            CURLOPT_USERAGENT => 'Codular Sample cURL Request',
            CURLOPT_HTTPHEADER => array(
                'Content-Type' => 'application/json',
                'Accept' => 'application/json'
            ),
            CURLOPT_POST => 1,
            CURLOPT_POSTFIELDS => array(
                'grant_type' => 'client_credentials',
                'client_id' => '4c863caa-953a-40ea-9fe9-837034149260',
                'client_secret' => 'rO2Tkw4vg0+Y/owP+D5kHsTfbOUfspr64lhY8/qJlmo=',
                'resource' => 'https://management.azure.com/'
            )

        ));

        // Send the request & save response to $resp
        $resp = curl_exec($curl);

        if(!curl_exec($curl)){
            die('Error: "' . curl_error($curl) . '" - Code: ' . curl_errno($curl));
        } else {
            $json = json_decode($resp, true);
            $accessTokenString = $json['access_token'];
        }

        // Close request to clear up some resources
        curl_close($curl);

        return $accessTokenString;
    }


    /**
     * Set a new fight in database
     *
     * @Route("/newFight")
     * @Method("POST")
     * @return View
     */
    public function setNewFight(Request $request) {
        $view = null;

        $fight = new Fight();
        $em = $this->getDoctrine()->getManager();

        $date = null;
        $fightState = null;
        $trainer1 = null;
        $trainer2 = null;

        // Get attributes from request
        if($request->request->get("date") != null) {
            $date = $request->request->get("date");
        }

        if($request->request->get("fightState") != null) {
            $fightState = $request->request->get("fightState");
        }

        if($request->request->get("trainer1") != null) {
            $trainer1 = $request->request->get("trainer1");
        }

        if($request->request->get("trainer2") != null) {
            $trainer2 = $request->request->get("trainer2");
        }

        if ($date != null && $fightState != null && $trainer1 != null && $trainer2 != null) {
            try {
                $fight->setDate($date);
                $fight->setFightState($em->getRepository('AppBundle:FightState')->findOneBy(array('name' => $fightState)));
                $fight->setTrainer1($em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $trainer1)));
                $fight->setTrainer2($em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $trainer2)));

                $em->persist($fight);
                $em->flush();

                $view = $this->view("Nouveau combat enregistré !", 200)->setFormat('json');

            } catch (Exception $e) {
                var_dump($e->getMessage());
            }
        }

        return $view;
    }


    /**
     * Get trainer fight
     *
     * @Route("/{trainer}")
     * @Method("GET")
     * @return View
     */
    public function getFight($trainer)
    {
        $view = null;
        $em = $this->getDoctrine()->getManager();

        try {
            $user = $em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $trainer));
            $fight = $em->getRepository('AppBundle:Fight')->findOneBy(array('trainer1' => $user));

            if ($fight != null) {
                $view = $this->view($fight, 200)->setFormat('json');
            } else {
                $view = $this->view(false, 204);
            }
        } catch (Exception $e) {
            var_dump($e->getMessage());
        }

        return $view;
    }

}