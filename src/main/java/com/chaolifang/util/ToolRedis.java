package com.chaolifang.util;

import org.springframework.stereotype.Component;

/**
 * @author 方法内加锁的方式 经过了jemeter压力测试没有问题 目前项目不适用 缺少依赖 tryGetLock releaseLock
 */
@Component
public class ToolRedis {
    /*@Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean tryGetLock(String lockKey, String requestId, int expireTime){
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.set(lockKey.getBytes(), requestId.getBytes(), Expiration.seconds(expireTime) , RedisStringCommands.SetOption.ifAbsent());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean releaseLock(String lockKey, String requestId) {
        try{
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                    Object result = redisConnection.eval(script.getBytes(), ReturnType.BOOLEAN,1,lockKey.getBytes(),requestId.getBytes());
                    if("true".equalsIgnoreCase(result.toString())){
                        return true;
                    }
                    return false;
                }
            });
        }catch (Exception e){

        }
        return false;
    }*/

    /**
     * 房天下业务代码实验
     * //审核定金合同
     *     public ApiResult auditDeposit(DepositMaterialSaveDTO dto, CurrentUserInfoDTO currentUser) {
     *         String contractId = dto.getContractId();
     *         if (ToolStr.isSpace(contractId)) {
     *             return ResultGenerator.fail("没有合同号");
     *         }
     *         ContractDeposit contractDepositDB = getContractDepositDB(contractId);
     *         if(contractDepositDB == null){
     *             return ResultGenerator.fail("没找到合同");
     *         }
     *         Integer depositStatus = contractDepositDB.getDepositStatus();
     *         if(depositStatus != EnumDepositStatus.待上传备件.getIndex()){
     *             return ResultGenerator.fail("流程错误");
     *         }
     *         String requestRandom = UUID.randomUUID().toString();
     *         String lockKey = "auditDepositBackup:"+contractId;
     *         boolean lock = redis.tryGetLock(lockKey, requestRandom, 10);
     *         if(lock){
     *             try{
     *                 Map<String, Object> paramsMap = new HashMap<>();//todo
     *                 //发起oa申请 todo
     *                 OAResult result = oaApprovalService.startOAApproval(ToolJson.mapToJson(paramsMap), "dto.getFileFild()", "key", "workflowcode", "", currentUser.getUserID());
     *                 if (!result.getCode().equals("1")) {
     *                     return ResultGenerator.fail("发起OA审批异常:" + result.getMessage().toString());
     *                 }
     *                 Approval_OA approvalOA = new Approval_OA();
     *                 String requestID = result.getMessage().toString();
     *                 approvalOA.setRequestId(requestID);
     *                 approvalOA.setOrderId(contractId);
     *                 approvalOA.setCity(contractDepositDB.getCity());
     *                 approvalOA.setOrderStatusBefore(EnumDepositStatus.待上传备件.getIndex());
     *                 approvalOA.setApprovalType(EnumApprovalType.定金合同审批.getIndex());
     *                 approvalOA.setApprovalSubType(null);
     *                 approvalOA.setFlowStatus(EnumApprovalFlowStatus.Approvaling.getIndex());
     *                 approvalOA.setFormFileds(ToolJson.mapToJson(paramsMap));
     *                 approvalOA.setFileFilds("dto.getFileFild()");//todo
     *                 approvalOA.setCreateTime(new Date());
     *                 approvalOA.setApplicantOAId(currentUser.getUserID() == null ? 0 : currentUser.getUserID());
     *                 approvalOA.setApplicantRealName(currentUser == null ? "" : currentUser.getRealName());
     *                 approvalOaDao.insert(approvalOA);
     *                 //变状态
     *                 contractDepositDB.setDepositStatus(EnumDepositStatus.待审核.getIndex());
     *                 contractDepositDB.setUpdateTime(new Date());
     *                 contractDepositDao.updateById(contractDepositDB);
     *             }finally {
     *                 redis.releaseLock(lockKey,requestRandom);
     *             }
     *             return ResultGenerator.successful();
     *         }else{
     *             redis.releaseLock(lockKey,requestRandom);
     *             return ResultGenerator.fail("请勿频繁操作");
     *         }
     *     }
     */
}
