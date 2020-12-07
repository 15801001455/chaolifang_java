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

    //秒杀 一个商品最多500件 大家一起  这种叫秒杀
    //参考代码
    /**
     *
     *@Resource
     *              private StringRedisTemplate stringRedisTemplate;
     *
     * 	    public Long flashSellByLuaScript(String skuCode,int num) {
     * 	    	//lua脚本语法
     * 	    	StringBuilder script= new StringBuilder();
     * 	    	script.append(" local buyNum = ARGV[1]" );//参数
     * 	    	script.append(" local goodsKey = KEYS[1] ");//key键
     * 	    	script.append(" local goodsNum = redis.call('get',goodsKey) "); // 调用方式，必须是redis.call 或者pcall结合redis里的方法
     * 	    	script.append(" if goodsNum >= buyNum  ");
     * 	    	script.append(" then redis.call('decrby',goodsKey,buyNum)   ");
     * 	    	script.append(" return buyNum   ");
     * 	    	script.append(" else    ");
     * 	    	script.append(" return '0'  ");
     * 	    	script.append(" end");
     *
     * 	    	DefaultRedisScript<String> longDefaultRedisScript = new DefaultRedisScript<>(script.toString(), String.class);
     * 	        String result = stringRedisTemplate.execute(longDefaultRedisScript, Collections.singletonList(skuCode),String.valueOf(num));
     * 	        return Long.valueOf(result);
     *
     * 	    	/* List<String> keys = new ArrayList<String>();
     * 	        RedisScript<Long> script = new DefaultRedisScript<Long>(
     * 	            "local size = redis.call('dbsize'); return size;", Long.class);
     * 	        Long dbsize = stringRedisTemplate.execute(script, keys, new Object[] {});
     * 	        System.out.println("sha1:" + script.getSha1());
     * 	        System.out.println("Lua:" + script.getScriptAsString());
     * 	        System.out.println("dbsize:" + dbsize);
     *
     * 	    }
     *
     *
     */
}
