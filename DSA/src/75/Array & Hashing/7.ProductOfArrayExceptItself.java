class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] prefix = new int[nums.length];
        int[] suffix = new int[nums.length];
        int[] ans = new int[nums.length];

        int product = 1;

        for(int i= 0; i <nums.length ; i++){
            prefix[i] = product;
            product*=nums[i];
        }
        product =1;
        for (int i = nums.length-1 ; i >=0 ; i--) {
            suffix[i] = product;
            product*=nums[i];
        }

        for (int i = 0; i < nums.length; i++) {
            ans[i] = prefix[i]*suffix[i];
        }

        return ans;
    }
}


// space optimization
public int[] productExceptSelf(int[] nums) {

    int[] ans = new int[nums.length];

    int prefix = 1;

    for(int i= 0; i <nums.length ; i++){
        ans[i] = prefix;
        prefix *=nums[i];
    }
    int suffix =1;
    for (int i = nums.length-1 ; i >=0 ; i--) {
        ans[i] *= suffix;
        suffix*=nums[i];
    }

    return ans;
}